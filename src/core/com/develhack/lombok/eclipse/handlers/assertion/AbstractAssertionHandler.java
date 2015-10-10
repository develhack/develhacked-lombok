package com.develhack.lombok.eclipse.handlers.assertion;

import static lombok.eclipse.handlers.EclipseHandlerUtil.*;

import java.util.Arrays;

import lombok.core.AnnotationValues;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Assignment;
import org.eclipse.jdt.internal.compiler.ast.CastExpression;
import org.eclipse.jdt.internal.compiler.ast.CompoundAssignment;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.StringLiteral;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;

import com.develhack.Conditions;
import com.develhack.annotation.assertion.Nullable;
import com.develhack.lombok.NameResolver;
import com.develhack.lombok.eclipse.handlers.AbstractEclipseHandler;

abstract class AbstractAssertionHandler<T extends java.lang.annotation.Annotation> extends AbstractEclipseHandler<T> {

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	protected boolean nullable;

	public AbstractAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	public void handle(AnnotationValues<T> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode variableNode = annotationNode.up();
		if (!(variableNode.get() instanceof AbstractVariableDeclaration)) {
			return;
		}

		AbstractVariableDeclaration variable = (AbstractVariableDeclaration) variableNode.get();

		if (!checkVariableType(variable)) return;

		nullable = findAnnotation(Nullable.class, variable.annotations) != null && !isPrimitiveType(variable);

		switch (variableNode.getKind()) {

			case FIELD:
				for (EclipseNode fieldNode : annotationNode.upFromAnnotationToFields()) {
					AbstractVariableDeclaration field = (AbstractVariableDeclaration) fieldNode.get();
					processConstructor(field);
					processSetter(field);
				}
				return;

			case ARGUMENT:
				AbstractMethodDeclaration method = (AbstractMethodDeclaration) variableNode.up().get();
				processArgument(method, (Argument) variable);
				return;

			default:
				annotationNode.addWarning(String
						.format("@%s is only applicable to the argument or field.", getAnnotationName()));
				return;
		}
	}

	protected abstract boolean checkVariableType(AbstractVariableDeclaration variable);

	protected abstract char[] getCheckMethodName();

	protected void processConstructor(AbstractVariableDeclaration field) {

		if (typeNode == null) return;
		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), String.valueOf(field.name));
		if (argumentName == null) return;

		char[] argumentNameChars = argumentName.toCharArray();
		for (AbstractMethodDeclaration method : clazz.methods) {

			if (!isConstructor(method)) continue;

			Argument argument = findArgument(method, argumentNameChars);
			if (argument == null) continue;

			processArgument(method, argument);
		}
	}

	protected void processSetter(AbstractVariableDeclaration field) {

		AbstractMethodDeclaration setter = findSetter(field);
		if (setter == null) return;

		Argument argument = setter.arguments[0];

		processArgument(setter, argument);
	}

	protected void processArgument(AbstractMethodDeclaration method, Argument argument) {

		EclipseNode methodNode = sourceNode.getNodeFor(method);
		if (methodNode == null) return;

		Annotation annotation = findAnnotation(getAnnotationHandledByThisHandler(), argument.annotations);

		if (isGenerated(method)) {
			if (annotation == null) {
				Annotation copied = copyAnnotation((Annotation) source, source);
				if (Conditions.isEmpty(argument.annotations)) {
					argument.annotations = new Annotation[] { copied };
				} else {
					argument.annotations = Arrays.copyOf(argument.annotations, argument.annotations.length + 1);
					argument.annotations[argument.annotations.length - 1] = copied;
				}
			}
		} else {
			if (annotation == null) {
				sourceNode.getNodeFor(argument).addError(String.format("missing the @%s.", getAnnotationName()));
				return;
			}
			if (!annotation.toString().equals(source.toString())) {
				sourceNode.getNodeFor(annotation).addError("different values specified as annotation for the field.");
				return;
			}
		}

		if (hasCheckMethodCall(method, argument)) return;

		MessageSend checkMethodCall = generateCheckMethodCall(argument);
		if (checkMethodCall == null) return;

		recursiveSetGeneratedBy(checkMethodCall);

		boolean replaced = false;
		if (hasConstructorCall(method)) {
			Expression[] constructorCallArguments = ((ConstructorDeclaration) method).constructorCall.arguments;
			replaced = replaceNameReferenceWithCheckExpression(constructorCallArguments, argument, checkMethodCall);
		}

		if (!replaced) {
			if (method.statements == null) {
				method.statements = new Statement[] { checkMethodCall };
			} else {
				Statement[] newStatements = new Statement[method.statements.length + 1];
				newStatements[0] = checkMethodCall;
				System.arraycopy(method.statements, 0, newStatements, 1, method.statements.length);
				method.statements = newStatements;
			}
		}

		methodNode.rebuild();
	}

	protected boolean hasCheckMethodCall(AbstractMethodDeclaration method, final AbstractVariableDeclaration variable) {

		class CheckMethodCallFinder extends ASTVisitor {

			private boolean found = false;

			@Override
			public boolean visit(MessageSend messageSend, BlockScope scope) {

				if (Conditions.isEmpty(messageSend.arguments)) return true;
				if (!(messageSend.arguments[0] instanceof StringLiteral)) return true;

				StringLiteral stringLiteral = (StringLiteral) messageSend.arguments[0];
				if (!Arrays.equals(stringLiteral.source(), variable.name)) return true;
				if (!Arrays.equals(messageSend.selector, getCheckMethodName())) return true;

				found = true;
				return false;
			}

			boolean found() {
				return found;
			}
		}

		CheckMethodCallFinder checkMethodCallFinder = new CheckMethodCallFinder();

		method.traverse(checkMethodCallFinder, (ClassScope) null);

		return checkMethodCallFinder.found();
	}

	protected MessageSend generateCheckMethodCall(AbstractVariableDeclaration variable) {

		Expression[] arguments = new Expression[2];
		arguments[0] = new StringLiteral(variable.name, pS, pE, 0);
		arguments[1] = new SingleNameReference(variable.name, p);

		MessageSend checkMethodCall = new MessageSend();
		checkMethodCall.sourceStart = pS;
		checkMethodCall.sourceEnd = checkMethodCall.statementEnd = pE;
		checkMethodCall.nameSourcePosition = p;
		checkMethodCall.receiver = generateNameReference(Conditions.class.getName());
		checkMethodCall.selector = getCheckMethodName();

		checkMethodCall.arguments = arguments;

		return checkMethodCall;
	}

	protected boolean replaceNameReferenceWithCheckExpression(Expression[] expressions, Argument argument,
			Expression checkExpression) {

		if (expressions == null) return false;

		boolean replaced = false;

		for (int i = 0; i < expressions.length; i++) {
			Expression expression = expressions[i];
			if (expression instanceof SingleNameReference) {
				if (Arrays.equals(((SingleNameReference) expression).token, argument.name)) {
					expressions[i] = checkExpression;
					replaced = true;
				}
				continue;
			}
			if (expression instanceof ArrayAllocationExpression) {
				replaced |= replaceNameReferenceWithCheckExpression(
						((ArrayAllocationExpression) expression).initializer.expressions, argument, checkExpression);
				continue;
			}
			if (expression instanceof MessageSend) {
				MessageSend messageSend = (MessageSend) expression;
				Expression[] receiver = new Expression[] { messageSend.receiver };
				replaced |= replaceNameReferenceWithCheckExpression(receiver, argument, checkExpression);
				messageSend.receiver = receiver[0];
				replaced |= replaceNameReferenceWithCheckExpression(messageSend.arguments, argument, checkExpression);
				continue;
			}
			if (expression instanceof CastExpression) {
				CastExpression castExpression = (CastExpression) expression;
				Expression[] casted = new Expression[] { castExpression.expression };
				replaced |= replaceNameReferenceWithCheckExpression(casted, argument, checkExpression);
				castExpression.expression = casted[0];
				continue;
			}
			if (expression instanceof CompoundAssignment) {
				CompoundAssignment compoundAssignment = (CompoundAssignment) expression;
				Expression[] lhs = new Expression[] { compoundAssignment.lhs };
				replaced |= replaceNameReferenceWithCheckExpression(lhs, argument, checkExpression);
				compoundAssignment.lhs = lhs[0];
				Expression[] right = new Expression[] { compoundAssignment.expression };
				replaced |= replaceNameReferenceWithCheckExpression(right, argument, checkExpression);
				compoundAssignment.expression = right[0];
				continue;
			}
			if (expression instanceof Assignment) {
				Assignment assignment = (Assignment) expression;
				Expression[] right = new Expression[] { assignment.expression };
				replaced |= replaceNameReferenceWithCheckExpression(right, argument, checkExpression);
				assignment.expression = right[0];
				continue;
			}
		}

		return replaced;
	}
}
