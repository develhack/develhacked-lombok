package com.develhack.lombok.javac.handlers.assertion;

import static lombok.javac.handlers.JavacHandlerUtil.*;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.javac.JavacNode;
import lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;

import com.develhack.Conditions;
import com.develhack.annotation.assertion.Nullable;
import com.develhack.lombok.NameResolver;
import com.develhack.lombok.javac.handlers.AbstractJavacHandler;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCAssignOp;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCExpressionStatement;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

abstract class AbstractAssertionHandler<T extends Annotation> extends AbstractJavacHandler<T> {

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	protected boolean nullable;

	public AbstractAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	public void handle(AnnotationValues<T> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode variableNode = annotationNode.up();
		if (!(variableNode.get() instanceof JCVariableDecl)) {
			return;
		}

		JCVariableDecl variable = (JCVariableDecl) variableNode.get();

		if (!checkVariableType(variable)) return;

		nullable = findAnnotation(Nullable.class, variable.mods.annotations) != null && !isPrimitiveType(variable);

		switch (variableNode.getKind()) {
			case FIELD:
				for (JavacNode fieldNode : annotationNode.upFromAnnotationToFields()) {
					JCVariableDecl field = (JCVariableDecl) fieldNode.get();
					processConstructor(field);
					processSetter(field);
				}
				return;

			case ARGUMENT:
				JCMethodDecl method = (JCMethodDecl) variableNode.up().get();
				processArgument(method, variable);
				return;

			default:
				annotationNode.addWarning(String
						.format("@%s is only applicable to the argument or field.", getAnnotationName()));
				return;
		}
	}

	protected abstract boolean checkVariableType(JCVariableDecl variable);

	protected abstract String getCheckMethodName();

	protected boolean isLiteral(String representation) {
		return !Character.isJavaIdentifierStart(representation.codePointAt(0));
	}

	protected void processConstructor(JCVariableDecl field) {

		if (typeNode == null) return;
		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), field.name.toString());
		if (argumentName == null) return;

		for (JCTree def : clazz.defs) {
			if (def.getKind() != Kind.METHOD) continue;

			JCMethodDecl method = (JCMethodDecl) def;
			if (!isConstructor(method)) continue;

			JCVariableDecl argument = findArgument(method, argumentName);
			if (argument == null) continue;

			processArgument(method, argument);
		}
	}

	protected void processSetter(JCVariableDecl field) {

		JCMethodDecl setter = findSetter(field);
		if (setter == null) return;

		JCVariableDecl argument = setter.params.head;

		processArgument(setter, argument);
	}

	protected void processArgument(JCMethodDecl method, JCVariableDecl argument) {

		JavacNode methodNode = sourceNode.getNodeFor(method);
		if (methodNode == null) return;

		JCAnnotation annotation = findAnnotation(getAnnotationHandledByThisHandler(), argument.mods.annotations);

		if (isGenerated(method)) {
			if (annotation == null) {
				argument.mods.annotations = argument.mods.annotations.append((JCAnnotation) ((JCAnnotation) source).clone());
			}
		} else {
			if (annotation == null) {
				sourceNode.getNodeFor(argument).addWarning(String.format("missing the @%s.", getAnnotationName()));
				return;
			}
			if (!annotation.toString().equals(source.toString())) {
				sourceNode.getNodeFor(annotation).addWarning("different values specified as annotation for the field.");
			}
		}

		if (hasCheckMethodCall(method, argument)) return;

		JCMethodInvocation checkMethodCall = generateCheckMethodCall(argument);
		if (checkMethodCall == null) return;

		if (hasConstructorCall(method)) {

			JCExpressionStatement expressionStatement = (JCExpressionStatement) method.body.stats.head;
			JCMethodInvocation constructorCall = (JCMethodInvocation) expressionStatement.expr;

			if (replaceArgumentAccessWithCheckExpression(constructorCall.args, argument, checkMethodCall)) {
				recursiveSetGeneratedBy(checkMethodCall);
			} else {
				List<JCStatement> checkMethodCallStatement = List.<JCStatement> of(maker.Exec(checkMethodCall));
				checkMethodCallStatement.tail = method.body.stats.tail;
				method.body.stats.tail = checkMethodCallStatement;
				recursiveSetGeneratedBy(checkMethodCallStatement.head);
			}

		} else {

			List<JCStatement> checkMethodCallStatement = List.<JCStatement> of(maker.Exec(checkMethodCall));
			checkMethodCallStatement.tail = method.body.stats;
			method.body.stats = checkMethodCallStatement;
			recursiveSetGeneratedBy(checkMethodCallStatement.head);
		}

		methodNode.rebuild();
	}

	protected boolean hasCheckMethodCall(JCMethodDecl method, final JCVariableDecl variable) {

		TreeScanner<Boolean, Void> checkMethodCallFinder = new TreeScanner<Boolean, Void>() {

			@Override
			public Boolean visitMethodInvocation(MethodInvocationTree node, Void p) {

				Boolean r = super.visitMethodInvocation(node, p);
				if (Boolean.TRUE.equals(r)) {
					return Boolean.TRUE;
				}

				JCMethodInvocation methodInvocation = (JCMethodInvocation) node;
				if (Conditions.isEmpty(methodInvocation.args)) return Boolean.FALSE;
				if (!(methodInvocation.args.head instanceof JCLiteral)) return Boolean.FALSE;

				JCLiteral literal = (JCLiteral) methodInvocation.args.head;
				if (literal.getKind() != Kind.STRING_LITERAL) return Boolean.FALSE;
				if (!variable.name.contentEquals(literal.value.toString())) return Boolean.FALSE;

				return getLastToken(methodInvocation.meth).equals(getCheckMethodName());
			}

			@Override
			public Boolean reduce(Boolean r1, Boolean r2) {
				return Boolean.TRUE.equals(r1) || Boolean.TRUE.equals(r2);
			}
		};

		return checkMethodCallFinder.scan(method, null);
	}

	protected JCMethodInvocation generateCheckMethodCall(JCVariableDecl variable) {

		Map<String, String> additionalCondtionMap;
		try {
			additionalCondtionMap = getAdditionalCondtionMap();
		} catch (AnnotationValueDecodeFail e) {
			return null;
		}

		JCExpression[] additionalCondtions = new JCExpression[additionalCondtionMap.size()];
		int i = 0;
		for (Entry<String, String> additionalCondtion : additionalCondtionMap.entrySet()) {
			String representation = additionalCondtion.getValue();
			if (isLiteral(representation)) {
				String fieldName = NameResolver.resolveConstantFieldName(variable.vartype.toString(), representation,
						isBoxedType(variable));
				if (fieldExists(fieldName, resolveTopTypeNode()) == MemberExistsResult.NOT_EXISTS) return null;

				additionalCondtions[i] = maker.Ident(sourceNode.toName(fieldName));

			} else {

				additionalCondtions[i] = generateNameReference(representation);
			}

			i++;
		}

		ListBuffer<JCExpression> arguments = new ListBuffer<JCExpression>();
		arguments.append(maker.Literal(variable.name.toString()));
		arguments.append(maker.Ident(variable.name));
		for (JCExpression condition : additionalCondtions) {
			arguments.append(condition);
		}

		return maker.Apply(List.<JCExpression> nil(), generateNameReference(Conditions.class.getName(), getCheckMethodName()),
				arguments.toList());
	}

	protected Map<String, String> getAdditionalCondtionMap() {
		return Collections.emptyMap();
	}

	protected boolean replaceArgumentAccessWithCheckExpression(List<JCExpression> expressions, JCVariableDecl argument,
			JCExpression checkExpression) {

		if (Conditions.isEmpty(expressions)) return false;

		boolean replaced = false;

		for (; expressions != null && expressions.head != null; expressions = expressions.tail) {

			if (expressions.head instanceof JCIdent) {
				if (((JCIdent) expressions.head).name.equals(argument.name)) {
					expressions.head = checkExpression;
					replaced = true;
				}
				continue;
			}
			if (expressions.head instanceof JCNewArray) {
				replaced |= replaceArgumentAccessWithCheckExpression(((JCNewArray) expressions.head).elems, argument,
						checkExpression);
				continue;
			}
			if (expressions.head instanceof JCMethodInvocation) {
				JCMethodInvocation methodInvocation = (JCMethodInvocation) expressions.head;
				if (methodInvocation.meth instanceof JCFieldAccess) {
					JCFieldAccess fieldAccess = (JCFieldAccess) methodInvocation.meth;
					if (fieldAccess.selected instanceof JCIdent) {
						if (((JCIdent) fieldAccess.selected).name.equals(argument.name)) {
							fieldAccess.selected = checkExpression;
							replaced = true;
						}
					}
				}
				replaced |= replaceArgumentAccessWithCheckExpression(methodInvocation.args, argument, checkExpression);
				continue;
			}
			if (expressions.head instanceof JCTypeCast) {
				JCTypeCast typeCast = (JCTypeCast) expressions.head;
				List<JCExpression> casted = List.of(typeCast.expr);
				replaced |= replaceArgumentAccessWithCheckExpression(casted, argument, checkExpression);
				typeCast.expr = casted.head;
				continue;
			}
			if (expressions.head instanceof JCAssignOp) {
				JCAssignOp assignOp = (JCAssignOp) expressions.head;
				List<JCExpression> lhs = List.of(assignOp.lhs);
				replaced |= replaceArgumentAccessWithCheckExpression(lhs, argument, checkExpression);
				assignOp.lhs = lhs.head;
				List<JCExpression> rhs = List.of(assignOp.rhs);
				replaced |= replaceArgumentAccessWithCheckExpression(rhs, argument, checkExpression);
				assignOp.rhs = rhs.head;
				continue;
			}
			if (expressions.head instanceof JCAssign) {
				JCAssign assign = (JCAssign) expressions.head;
				List<JCExpression> rhs = List.of(assign.rhs);
				replaced |= replaceArgumentAccessWithCheckExpression(rhs, argument, checkExpression);
				assign.rhs = rhs.head;
				continue;
			}
		}

		return replaced;
	}
}
