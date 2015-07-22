package com.develhack.lombok.javac.handlers;

import static lombok.javac.Javac.isPrimitive;
import static lombok.javac.handlers.JavacHandlerUtil.*;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.handlers.JavacHandlerUtil;

import com.develhack.Conditions;
import com.develhack.lombok.NameResolver;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;

public abstract class AbstractJavacHandler<T extends Annotation> extends JavacAnnotationHandler<T> {

	private static final Pattern BOXED_TYPE_NAME_PATTERN = Pattern
			.compile("^(java\\.lang\\.)?(Boolean|Byte|Short|Integer|Long|Float|Double|Character)$");

	protected final Class<T> annotationType;

	protected AnnotationValues<T> annotationValues;
	protected JavacNode sourceNode;
	protected JCTree source;
	protected JavacTreeMaker maker;
	public JavacNode typeNode;

	public AbstractJavacHandler(Class<T> annotationType) {
		this.annotationType = annotationType;
	}

	@Override
	public Class<T> getAnnotationHandledByThisHandler() {
		return annotationType;
	}

	@Override
	public void handle(AnnotationValues<T> annotationValues, JCAnnotation ast, JavacNode annotationNode) {
		this.annotationValues = annotationValues;
		this.sourceNode = annotationNode;
		this.source = annotationNode.get();
		this.maker = annotationNode.getTreeMaker();
		for (this.typeNode = sourceNode; this.typeNode != null && this.typeNode.getKind() != Kind.TYPE;) {
			this.typeNode = this.typeNode.up();
		}
	}

	protected String getAnnotationName() {
		return getAnnotationHandledByThisHandler().getSimpleName();
	}

	protected boolean modifiersMatches(long mod1, long mod2, long... masks) {
		for (long mask : masks) {
			if ((mod1 & mask) != (mod2 & mask)) return false;
		}
		return true;
	}

	protected boolean isPrimitiveType(JCVariableDecl variable) {
		return isPrimitive(variable.vartype);
	}

	protected boolean isBoxedType(JCVariableDecl variable) {
		return BOXED_TYPE_NAME_PATTERN.matcher(variable.vartype.toString()).matches();
	}

	protected boolean isReferenceType(JCVariableDecl variable) {
		return !isPrimitive(variable.vartype);
	}

	protected boolean isBoolean(JCVariableDecl variable) {
		return getLastToken(variable.vartype).equalsIgnoreCase("boolean");
	}

	protected boolean isPrimitiveNumber(JCVariableDecl variable) {
		return isPrimitiveType(variable) && !isBoolean(variable);
	}

	protected boolean isBoxedNumber(JCVariableDecl variable) {
		return isBoxedType(variable) && !isBoolean(variable);
	}

	protected boolean isTransient(JCVariableDecl variable) {
		return (variable.mods.flags & Flags.TRANSIENT) != 0;
	}

	protected boolean isAbstract(JCMethodDecl method) {
		return method.body == null;
	}

	protected boolean isConstructor(JCMethodDecl method) {
		return method.name.contentEquals("<init>");
	}

	protected boolean hasConstructorCall(JCMethodDecl method) {
		if (method.body == null || method.body.stats.isEmpty()) return false;
		return method.body.stats.head != null && JavacHandlerUtil.isConstructorCall(method.body.stats.head);
	}

	protected JCVariableDecl findArgument(JCMethodDecl method, String argumentName) {

		if (Conditions.isEmpty(method.params)) return null;

		for (JCVariableDecl argument : method.params) {
			if (argument.name.contentEquals(argumentName)) return argument;
		}
		return null;
	}

	protected JCMethodDecl findGetter(JCVariableDecl field) {

		if (typeNode == null) return null;

		String getterName = NameResolver.resolveGetterName(sourceNode.getAst(), field.name.toString(), isBoolean(field));
		if (getterName == null) {
			sourceNode.addWarning(String.format("getter name of '%s' cannot be resolved.", sourceNode.getName()));
			return null;
		}

		for (JavacNode child : typeNode.down()) {

			if (child.getKind() != Kind.METHOD) continue;

			JCMethodDecl method = (JCMethodDecl) child.get();
			if (method.body == null) continue;
			if (!modifiersMatches(method.mods.flags, field.mods.flags, Flags.STATIC)) continue;
			if (!method.getName().contentEquals(getterName)) continue;

			List<JCVariableDecl> arguments = method.getParameters();
			if (!Conditions.isEmpty(arguments)) continue;

			return method;
		}

		return null;
	}

	protected JCMethodDecl findSetter(JCVariableDecl field) {

		if (typeNode == null) return null;

		String setterName = NameResolver.resolveSetterName(sourceNode.getAst(), field.name.toString(), isBoolean(field));
		if (setterName == null) {
			sourceNode.addWarning(String.format("setter name of '%s' cannot be resolved.", sourceNode.getName()));
			return null;
		}

		for (JavacNode child : typeNode.down()) {

			if (child.getKind() != Kind.METHOD) continue;

			JCMethodDecl method = (JCMethodDecl) child.get();
			if (method.body == null) continue;
			if (!modifiersMatches(method.mods.flags, field.mods.flags, Flags.STATIC)) continue;
			if (!method.getName().contentEquals(setterName)) continue;

			List<JCVariableDecl> arguments = method.getParameters();
			if (arguments == null || arguments.size() != 1) continue;

			return method;
		}

		return null;
	}

	protected <E extends java.lang.annotation.Annotation> JCAnnotation findAnnotation(Class<E> annotationType,
			List<JCAnnotation> annotations) {

		if (Conditions.isEmpty(annotations)) return null;

		String annotationName = annotationType.getSimpleName();
		for (JCAnnotation annotation : annotations) {
			if (getLastToken(annotation.annotationType).equals(annotationName)) {
				return annotation;
			}
		}

		return null;
	}

	protected <E extends java.lang.annotation.Annotation> AnnotationValues<E> findAnnotationValues(Class<E> annotationType,
			List<JCAnnotation> annotations) {

		JCAnnotation annotation = findAnnotation(annotationType, annotations);
		if (annotation == null) return null;

		JavacNode annotationNode = sourceNode.getNodeFor(annotation);
		if (annotationNode == null) return null;

		return createAnnotation(annotationType, annotationNode);
	}

	protected boolean checkPrimitiveType(JCVariableDecl variable) {

		if (isPrimitiveType(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the primitive type.", getAnnotationName()));
		return false;
	}

	protected boolean checkReferenceType(JCVariableDecl variable) {

		if (isReferenceType(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the reference type.", getAnnotationName()));
		return false;
	}

	protected boolean checkNumericalType(JCVariableDecl variable) {

		if (isPrimitiveNumber(variable) || isBoxedNumber(variable)) return true;

		sourceNode.addWarning(String.format("@%s is only applicable to the numerical type.", getAnnotationName()));
		return false;
	}

	protected boolean checkRealType(JCVariableDecl variable) {

		if (isPrimitiveNumber(variable)) {
			switch (getLastToken(variable.vartype).charAt(0)) {
				case 'f':
				case 'd':
					return true;
				default:
			}
		}
		if (isBoxedNumber(variable)) {
			switch (getLastToken(variable.vartype).charAt(0)) {
				case 'F':
				case 'D':
					return true;
				default:
			}
		}

		sourceNode.addWarning(String.format("@%s is only applicable to the real type.", getAnnotationName()));
		return false;
	}

	protected JCExpression generateNameReference(String fqn, String... selectors) {
		JCExpression selected = chainDots(sourceNode, fqn.split("\\."));
		for (String selector : selectors) {
			selected = maker.Select(selected, sourceNode.toName(selector));
		}
		return selected;
	}

	protected <E extends JCTree> E recursiveSetGeneratedBy(E node) {
		return JavacHandlerUtil.recursiveSetGeneratedBy(node, source, sourceNode.getContext());
	}

	protected String getLastToken(JCTree node) {
		String token = node.toString();
		int lastDot = token.lastIndexOf('.');
		if (lastDot >= 0) token = token.substring(lastDot + 1);
		return token;
	}

	protected JavacNode resolveTopTypeNode() {

		JavacNode topTypeNode = typeNode;
		for (JavacNode node = typeNode.up(); node != null; node = node.up()) {
			if (node.getKind() == Kind.TYPE) topTypeNode = node;
		}

		return topTypeNode;
	}
}
