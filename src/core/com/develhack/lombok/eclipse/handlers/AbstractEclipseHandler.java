package com.develhack.lombok.eclipse.handlers;

import static lombok.eclipse.Eclipse.*;
import static lombok.eclipse.handlers.EclipseHandlerUtil.createAnnotation;

import java.util.Arrays;
import java.util.regex.Pattern;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.eclipse.Eclipse;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.SetGeneratedByVisitor;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NameReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.SingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;

import com.develhack.Conditions;
import com.develhack.lombok.NameResolver;

public abstract class AbstractEclipseHandler<T extends java.lang.annotation.Annotation> extends EclipseAnnotationHandler<T> {

	private static final Pattern BOXED_TYPE_NAME_PATTERN = Pattern
			.compile("^(java\\.lang\\.)?(Boolean|Byte|Short|Integer|Long|Float|Double|Character)$");

	protected final Class<T> annotationType;

	protected AnnotationValues<T> annotationValues;
	protected EclipseNode sourceNode;
	protected ASTNode source;
	protected int pS, pE;
	protected long p;
	protected EclipseNode typeNode;

	public AbstractEclipseHandler(Class<T> annotationType) {
		this.annotationType = annotationType;
	}

	@Override
	public Class<T> getAnnotationHandledByThisHandler() {
		return annotationType;
	}

	@Override
	public void preHandle(AnnotationValues<T> annotationValues, org.eclipse.jdt.internal.compiler.ast.Annotation ast,
			EclipseNode annotationNode) {
		this.annotationValues = annotationValues;
		this.sourceNode = annotationNode;
		this.source = annotationNode.get();
		this.pS = source.sourceStart;
		this.pE = source.sourceEnd;
		this.p = (long) pS << 32 | pE;
		for (this.typeNode = sourceNode; this.typeNode != null && this.typeNode.getKind() != Kind.TYPE;) {
			this.typeNode = this.typeNode.up();
		}
	}

	@Override
	public void handle(AnnotationValues<T> annotationValues, org.eclipse.jdt.internal.compiler.ast.Annotation ast,
			EclipseNode annotationNode) {
		this.annotationValues = annotationValues;
		this.sourceNode = annotationNode;
		this.source = annotationNode.get();
		this.pS = source.sourceStart;
		this.pE = source.sourceEnd;
		this.p = (long) pS << 32 | pE;
		for (this.typeNode = sourceNode; this.typeNode != null && this.typeNode.getKind() != Kind.TYPE;) {
			this.typeNode = this.typeNode.up();
		}
	}

	protected String getAnnotationName() {
		return getAnnotationHandledByThisHandler().getSimpleName();
	}

	protected boolean modifiersMatches(int mod1, int mod2, int... masks) {
		for (int mask : masks) {
			if ((mod1 & mask) != (mod2 & mask)) return false;
		}
		return true;
	}

	protected boolean isReferenceType(EclipseNode variableNode) {

		if (!(variableNode.get() instanceof AbstractVariableDeclaration)) return false;

		AbstractVariableDeclaration variable = (AbstractVariableDeclaration) variableNode.get();
		return !Eclipse.isPrimitive(variable.type);
	}

	protected boolean isPrimitiveType(AbstractVariableDeclaration variable) {
		return isPrimitive(variable.type);
	}

	protected boolean isBoxedType(AbstractVariableDeclaration variable) {
		if (variable.type.dimensions() > 0) return false;
		return BOXED_TYPE_NAME_PATTERN.matcher(toQualifiedName(variable.type.getTypeName())).matches();
	}

	protected boolean isReferenceType(AbstractVariableDeclaration variable) {
		return !isPrimitive(variable.type);
	}

	protected boolean isBoolean(AbstractVariableDeclaration variable) {
		if (variable.type.dimensions() > 0) return false;
		char[] token = variable.type.getLastToken();
		if (token.length != "boolean".length()) return false;
		if (token[0] != 'b' && token[0] != 'B') return false;
		if (token[1] != 'o' || token[2] != 'o' || token[3] != 'l' || token[4] != 'e' || token[5] != 'a' || token[6] != 'n') return false;
		return true;
	}

	protected boolean isPrimitiveNumber(AbstractVariableDeclaration variable) {
		return isPrimitiveType(variable) && variable.type.getLastToken()[0] != 'b';
	}

	protected boolean isBoxedNumber(AbstractVariableDeclaration variable) {
		return isBoxedType(variable) && variable.type.getLastToken()[0] != 'B';
	}

	protected boolean isTransient(AbstractVariableDeclaration variable) {
		return (variable.modifiers & ClassFileConstants.AccTransient) != 0;
	}

	protected boolean isAbstract(AbstractMethodDeclaration method) {
		return method.isAbstract();
	}

	protected boolean isConstructor(AbstractMethodDeclaration method) {
		return method.isConstructor();
	}

	protected boolean hasConstructorCall(AbstractMethodDeclaration method) {
		if (!(method instanceof ConstructorDeclaration)) return false;
		return ((ConstructorDeclaration) method).constructorCall != null;
	}

	protected Argument findArgument(AbstractMethodDeclaration method, char[] argumentName) {

		if (Conditions.isEmpty(method.arguments)) return null;

		for (Argument argument : method.arguments) {
			if (Arrays.equals(argument.name, argumentName)) return argument;
		}
		return null;
	}

	protected AbstractMethodDeclaration findGetter(AbstractVariableDeclaration field) {

		if (typeNode == null) return null;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		String getterName = NameResolver.resolveGetterName(sourceNode.getAst(), String.valueOf(field.name), isBoolean(field));
		if (getterName == null) {
			sourceNode.addWarning(String.format("getter name of '%s' cannot be resolved.", sourceNode.getName()));
			return null;
		}

		char[] getterNameChars = getterName.toCharArray();
		for (AbstractMethodDeclaration method : clazz.methods) {
			if (method.isAbstract()) continue;
			if (!modifiersMatches(method.modifiers, field.modifiers, ClassFileConstants.AccStatic)) continue;
			if (!Arrays.equals(method.selector, getterNameChars)) continue;

			Argument[] arguments = method.arguments;
			if (!Conditions.isEmpty(arguments)) continue;

			return method;
		}

		return null;
	}

	protected AbstractMethodDeclaration findSetter(AbstractVariableDeclaration field) {

		if (typeNode == null) return null;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		String setterName = NameResolver.resolveSetterName(sourceNode.getAst(), String.valueOf(field.name), isBoolean(field));
		if (setterName == null) {
			sourceNode.addWarning(String.format("setter name of '%s' cannot be resolved.", sourceNode.getName()));
			return null;
		}

		char[] setterNameChars = setterName.toCharArray();
		for (AbstractMethodDeclaration method : clazz.methods) {
			if (method.isAbstract()) continue;
			if (!modifiersMatches(method.modifiers, field.modifiers, ClassFileConstants.AccStatic)) continue;
			if (!Arrays.equals(method.selector, setterNameChars)) continue;

			Argument[] arguments = method.arguments;
			if (arguments == null || arguments.length != 1) continue;

			return method;
		}

		return null;
	}

	protected <E extends java.lang.annotation.Annotation> Annotation findAnnotation(Class<E> annotationType,
			Annotation[] annotations) {

		if (Conditions.isEmpty(annotations)) return null;

		char[] annotationName = annotationType.getSimpleName().toCharArray();
		for (org.eclipse.jdt.internal.compiler.ast.Annotation annotation : annotations) {
			if (Arrays.equals(annotation.type.getLastToken(), annotationName)) {
				return annotation;
			}
		}

		return null;
	}

	protected <E extends java.lang.annotation.Annotation> AnnotationValues<E> findAnnotationValues(Class<E> annotationType,
			Annotation[] annotations) {

		Annotation annotation = findAnnotation(annotationType, annotations);
		if (annotation == null) return null;

		EclipseNode annotationNode = sourceNode.getNodeFor(annotation);
		if (annotationNode == null) return null;

		return createAnnotation(annotationType, annotationNode);
	}

	protected boolean checkPrimitiveType(AbstractVariableDeclaration variable) {

		if (isPrimitiveType(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the primitive type.", getAnnotationName()));
		return false;
	}

	protected boolean checkReferenceType(AbstractVariableDeclaration variable) {

		if (isReferenceType(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the reference type.", getAnnotationName()));
		return false;
	}

	protected boolean checkNumericalType(AbstractVariableDeclaration variable) {

		if (isPrimitiveNumber(variable) || isBoxedNumber(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the numerical type.", getAnnotationName()));
		return false;
	}

	protected boolean checkRealType(AbstractVariableDeclaration variable) {

		if (isPrimitiveNumber(variable)) {
			switch (variable.type.getLastToken()[0]) {
				case 'f':
				case 'd':
					return true;
				default:
			}
		}
		if (isBoxedNumber(variable)) {
			switch (variable.type.getLastToken()[0]) {
				case 'F':
				case 'D':
					return true;
				default:
			}
		}

		sourceNode.addWarning(String.format("@%s is only applicable to the real type.", getAnnotationName()));
		return false;
	}

	protected NameReference generateNameReference(String name) {

		NameReference nameReference;

		if (name.indexOf('.') >= 0) {
			char[][] nameTokens = fromQualifiedName(name);
			nameReference = new QualifiedNameReference(nameTokens, poss(source, nameTokens.length), pS, pE);
		} else {
			nameReference = new SingleNameReference(name.toCharArray(), pos(source));
		}
		nameReference.sourceStart = pS;
		nameReference.sourceEnd = nameReference.statementEnd = pE;

		return nameReference;
	}

	protected TypeReference generateTypeReference(String name) {

		TypeReference typeReference;

		if (name.indexOf('.') >= 0) {
			char[][] nameTokens = fromQualifiedName(name);
			typeReference = new QualifiedTypeReference(nameTokens, poss(source, nameTokens.length));
		} else {
			typeReference = new SingleTypeReference(name.toCharArray(), pos(source));
		}
		typeReference.sourceStart = pS;
		typeReference.sourceEnd = typeReference.statementEnd = pE;

		return typeReference;
	}

	protected <E extends ASTNode> E recursiveSetGeneratedBy(E node) {

		SetGeneratedByVisitor visitor = new SetGeneratedByVisitor(source);

		if (node instanceof AbstractMethodDeclaration) {
			((AbstractMethodDeclaration) node).traverse(visitor, (ClassScope) null);
		} else if (node instanceof FieldDeclaration) {
			((FieldDeclaration) node).traverse(visitor, (MethodScope) null);
		} else {
			node.traverse(visitor, null);
		}

		return node;
	}

	protected EclipseNode resolveTopTypeNode() {

		EclipseNode topTypeNode = typeNode;
		for(EclipseNode node = typeNode.up(); node != null; node = node.up()) {
			if(node.getKind() == Kind.TYPE) topTypeNode = node;
		}

		return topTypeNode;
	}

	protected char[] toCharArray(String string) {
		return (string == null) ? null : string.toCharArray();
	}
}
