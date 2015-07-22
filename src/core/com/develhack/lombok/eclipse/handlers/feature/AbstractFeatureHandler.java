package com.develhack.lombok.eclipse.handlers.feature;

import static lombok.eclipse.Eclipse.*;
import static lombok.eclipse.handlers.EclipseHandlerUtil.*;
import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.EclipseHandlerUtil;
import lombok.eclipse.handlers.EclipseHandlerUtil.FieldAccess;
import lombok.eclipse.handlers.HandleEqualsAndHashCode;
import lombok.eclipse.handlers.HandleToString;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.Assignment;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldReference;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;
import org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.StringLiteral;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;

import com.develhack.Conditions;
import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Equatable;
import com.develhack.annotation.feature.ExcludedFrom;
import com.develhack.annotation.feature.Stringable;
import com.develhack.lombok.NameResolver;
import com.develhack.lombok.eclipse.handlers.AbstractEclipseHandler;

public abstract class AbstractFeatureHandler<T extends java.lang.annotation.Annotation> extends AbstractEclipseHandler<T> {

	public static final int PRIORITY = Integer.MIN_VALUE;

	public AbstractFeatureHandler(Class<T> annotationType) {
		super(annotationType);
	}

	protected int toModifier(Access access) {
		switch (access) {
			case DEFAULT:
				return AccDefault;
			case PUBLIC:
				return AccPublic;
			case PROTECTED:
				return AccProtected;
			case PRIVATE:
				return AccPrivate;
			default:
				throw new AssertionError(access.toString());
		}
	}

	protected boolean argumentTypesEquals(AbstractMethodDeclaration method, AbstractVariableDeclaration[] arguments) {

		if (Conditions.isEmpty(method.arguments)) {
			if (!Conditions.isEmpty(arguments)) return false;
			return true;
		}

		if (Conditions.isEmpty(arguments)) return false;
		if (method.arguments.length != arguments.length) return false;

		for (int i = 0; i < arguments.length; i++) {
			if (!Arrays.deepEquals(method.arguments[i].type.getTypeName(), arguments[i].type.getTypeName())) {
				return false;
			}
		}

		return true;
	}

	protected boolean isExcludedFrom(FieldDeclaration field, Class<? extends java.lang.annotation.Annotation> feature) {

		if (Conditions.isEmpty(field.annotations)) return false;

		AnnotationValues<ExcludedFrom> excludeFrom = findAnnotationValues(ExcludedFrom.class, field.annotations);
		if (excludeFrom == null) return false;

		try {
			for (Class<? extends java.lang.annotation.Annotation> specifiedFeature : excludeFrom.getInstance().value()) {
				if (feature.equals(specifiedFeature)) return true;
			}
		} catch (AnnotationValueDecodeFail e) {}

		return false;
	}

	protected ConstructorDeclaration findConstructor(FieldDeclaration[] fieldsToBeInitialize) {

		if (typeNode == null) return null;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();
		if (Conditions.isEmpty(clazz.methods)) return null;

		for (AbstractMethodDeclaration method : clazz.methods) {
			if (!method.isConstructor()) continue;
			if (argumentTypesEquals(method, fieldsToBeInitialize)) return (ConstructorDeclaration) method;
		}

		return null;
	}

	protected FieldDeclaration[] findFields(int requiredModifiers, int excludedModifiers,
			Class<? extends java.lang.annotation.Annotation> excludeFeature, boolean notInitializedOnly) {

		if (typeNode == null) return null;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();
		if (Conditions.isEmpty(clazz.fields)) return null;

		List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
		for (FieldDeclaration field : clazz.fields) {

			if ((field.modifiers & requiredModifiers) != requiredModifiers) continue;
			if ((field.modifiers & excludedModifiers) != 0) continue;
			if (notInitializedOnly && field.initialization != null) continue;
			if (isExcludedFrom(field, excludeFeature)) continue;

			fields.add(field);
		}

		return fields.toArray(new FieldDeclaration[fields.size()]);
	}

	protected TypeReference findSuperInterface(Class<?> interfaceType, TypeReference[] superInterfaces) {

		if (Conditions.isEmpty(superInterfaces)) return null;

		char[] interfaceName = interfaceType.getSimpleName().toCharArray();
		for (TypeReference superInterface : superInterfaces) {
			if (Arrays.equals(superInterface.getLastToken(), interfaceName)) return superInterface;
		}

		return null;
	}

	protected List<String> findExcludeFields(Class<? extends java.lang.annotation.Annotation> feature) {

		if (typeNode == null) return Collections.emptyList();

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();
		if (Conditions.isEmpty(clazz.fields)) return null;

		List<String> excludes = new ArrayList<String>();
		for (FieldDeclaration field : clazz.fields) {
			if (isExcludedFrom(field, feature)) excludes.add(String.valueOf(field.name));
		}

		return excludes;
	}

	protected ConstructorDeclaration generateConstructor(FieldDeclaration[] fieldsToBeInitialize,
			TypeDeclaration clazz, int modifiers) {

		ConstructorDeclaration constructor;
		constructor = new ConstructorDeclaration(clazz.compilationResult);
		constructor.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		constructor.modifiers = modifiers;
		constructor.selector = clazz.name;
		constructor.constructorCall = new ExplicitConstructorCall(ExplicitConstructorCall.ImplicitSuper);
		constructor.constructorCall.sourceStart = pS;
		constructor.constructorCall.sourceEnd = pE;
		constructor.arguments = new Argument[fieldsToBeInitialize.length];
		constructor.statements = new Statement[fieldsToBeInitialize.length];
		constructor.declarationSourceStart = constructor.bodyStart = constructor.sourceStart = pS;
		constructor.declarationSourceEnd = constructor.bodyEnd = constructor.sourceEnd = pE;

		for (int i = 0; i < fieldsToBeInitialize.length; i++) {

			String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(),
					String.valueOf(fieldsToBeInitialize[i].name));

			Expression argumentReference = generateNameReference(argumentName);
			FieldReference fieldReference = generateFieldReference(fieldsToBeInitialize[i]);

			Argument argument = new Argument(argumentName.toCharArray(), p, copyType(fieldsToBeInitialize[i].type, source),
					ClassFileConstants.AccFinal);
			argument.sourceStart = argument.declarationSourceStart = pS;
			argument.sourceEnd = argument.declarationEnd = argument.declarationSourceEnd = pE;
			constructor.arguments[i] = argument;

			Assignment assignment = new Assignment(fieldReference, argumentReference, pE);
			assignment.sourceStart = pS;
			assignment.sourceEnd = assignment.statementEnd = pE;
			constructor.statements[i] = assignment;
		}

		return constructor;
	}

	protected MethodDeclaration generateGetter(FieldDeclaration field, int modifiers) {

		if (typeNode == null) return null;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		boolean isBoolean = isBoolean(field);
		char[] getterName = toCharArray(NameResolver.resolveGetterName(sourceNode.getAst(), String.valueOf(field.name),
				isBoolean));

		Expression fieldReference = generateFieldReference(field);
		Statement returnStatement = new ReturnStatement(fieldReference, pS, pE);

		MethodDeclaration getter = new MethodDeclaration(clazz.compilationResult);
		getter.modifiers = modifiers;
		getter.returnType = copyType(field.type, source);
		getter.arguments = null;
		getter.selector = getterName;
		getter.binding = null;
		getter.thrownExceptions = null;
		getter.typeParameters = null;
		getter.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		getter.annotations = copyAnnotations(source);
		getter.statements = new Statement[] { returnStatement };
		getter.bodyStart = getter.declarationSourceStart = getter.sourceStart = source.sourceStart;
		getter.bodyEnd = getter.declarationSourceEnd = getter.sourceEnd = source.sourceEnd;

		return getter;
	}

	protected MethodDeclaration generateSetter(FieldDeclaration field, int modifiers) {

		if (typeNode == null) return null;

		String fieldName = String.valueOf(field.name);

		char[] setterName = toCharArray(NameResolver.resolveSetterName(sourceNode.getAst(), fieldName, isBoolean(field)));
		String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), fieldName);

		Expression argumentReference = generateNameReference(argumentName);
		FieldReference fieldReference = generateFieldReference(field);

		Assignment assignment = new Assignment(fieldReference, argumentReference, (int) p);
		assignment.sourceStart = pS;
		assignment.sourceEnd = assignment.statementEnd = pE;

		Argument argument = new Argument(argumentName.toCharArray(), p, copyType(field.type, source), Modifier.FINAL);
		argument.sourceStart = pS;
		argument.sourceEnd = pE;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();
		MethodDeclaration setter = new MethodDeclaration(clazz.compilationResult);
		setter.modifiers = modifiers;
		setter.returnType = TypeReference.baseTypeReference(TypeIds.T_void, 0);
		setter.arguments = new Argument[] { argument };
		setter.selector = setterName;
		setter.binding = null;
		setter.thrownExceptions = null;
		setter.typeParameters = null;
		setter.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		setter.annotations = copyAnnotations(source);
		setter.statements = new Statement[] { assignment };
		setter.bodyStart = setter.declarationSourceStart = setter.sourceStart = source.sourceStart;
		setter.bodyEnd = setter.declarationSourceEnd = setter.sourceEnd = source.sourceEnd;

		return setter;
	}

	protected FieldReference generateFieldReference(FieldDeclaration field) {

		if (typeNode == null) return null;

		FieldReference fieldRef = new FieldReference(field.name, p);

		if ((field.modifiers & ClassFileConstants.AccStatic) != 0) {
			fieldRef.receiver = generateNameReference(typeNode.getName());
		} else {
			fieldRef.receiver = new ThisReference(pS, pE);
		}

		return fieldRef;
	}

	protected void supplementFinalModifier() {

		if (typeNode == null) return;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		clazz.modifiers |= AccFinal;
		typeNode.getAst().setChanged();
	}

	protected void supplementConstructor(Access access, FieldDeclaration[] fieldsToBeInitialize) {

		if (typeNode == null) return;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		int modifiers = toModifier(access);

		ConstructorDeclaration constructor = findConstructor(fieldsToBeInitialize);
		if (constructor != null) {
			if (access == Access.NONE || (constructor.modifiers & modifiers) != modifiers) {
				EclipseNode constructorNode = sourceNode.getNodeFor(constructor);
				if (constructorNode != null) {
					constructorNode.addWarning(String.format("conflicted with the %s.", source));
				}
			}
			return;
		}

		constructor = generateConstructor(fieldsToBeInitialize, clazz, modifiers);

		injectMethod(recursiveSetGeneratedBy(constructor));
	}

	protected void supplementUncallableConstructor() {

		if (typeNode == null) return;

		EclipseNode defaultConstructorNode = null;
		for (EclipseNode child : typeNode.down()) {
			if (child.getKind() != Kind.METHOD) continue;
			AbstractMethodDeclaration method = (AbstractMethodDeclaration) child.get();
			if (!isConstructor(method)) continue;
			if (method.isDefaultConstructor()) {
				defaultConstructorNode = child;
				method.bits &= (~ASTNode.Bit8);
				break;
			}
			if ((method.modifiers & AccPrivate) == 0) {
				child.addWarning(String.format("class annotated by @%s must not have callable constructor.",
						getAnnotationName()));
				continue;
			}
		}

		if (defaultConstructorNode == null) {
			return;
		}

		AllocationExpression assertionError = new AllocationExpression();
		assertionError.type = generateTypeReference(AssertionError.class.getName());
		assertionError.sourceStart = pS;
		assertionError.sourceEnd = assertionError.statementEnd = pE;

		ConstructorDeclaration defaultConstructor = (ConstructorDeclaration) defaultConstructorNode.get();
		defaultConstructor.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		defaultConstructor.modifiers = ClassFileConstants.AccPrivate;
		defaultConstructor.statements = new Statement[] { new ThrowStatement(assertionError, pS, pE) };
		defaultConstructor.sourceStart = pS;
		defaultConstructor.sourceEnd = pE;

		recursiveSetGeneratedBy(defaultConstructor);
	}

	protected void supplementGetter(FieldDeclaration field, Access access) {

		if (typeNode == null) return;

		AbstractMethodDeclaration getter = findGetter(field);
		if (access == Access.NONE) {
			if (getter != null) {
				sourceNode.getNodeFor(getter).addWarning(String.format("conflicted with the %s.", source));
			}
			return;
		}

		int modifiers = toModifier(access) | (field.modifiers & AccStatic);

		if (getter == null) {
			getter = generateGetter(field, modifiers);
			if (getter != null) injectMethod(recursiveSetGeneratedBy(getter));
			return;
		}

		if (isAbstract(getter)) {
			sourceNode.addWarning(String.format("abstract getter of '%s' already exists.", String.valueOf(field.name)));
			return;
		}

		if (!modifiersMatches(getter.modifiers, modifiers, AccStatic, AccPublic, AccProtected, AccPrivate)) {
			sourceNode.getNodeFor(getter).addWarning(String.format("conflicted with the %s.", source));
			return;
		}
	}

	protected void supplementSetter(FieldDeclaration field, Access access) {

		if (typeNode == null) return;

		if ((field.modifiers & AccFinal) != 0) {
			return;
		}

		AbstractMethodDeclaration setter = findSetter(field);
		if (access == Access.NONE) {
			if (setter != null) {
				sourceNode.getNodeFor(setter).addWarning(String.format("conflicted with the %s.", source));
			}
			return;
		}

		int modifiers = toModifier(access) | (field.modifiers & AccStatic);

		if (setter == null) {
			setter = generateSetter(field, modifiers);
			if (setter != null) injectMethod(recursiveSetGeneratedBy(setter));
			return;
		}

		if (isAbstract(setter)) {
			sourceNode.addWarning(String.format("abstract setter of '%s' already exists.", String.valueOf(field.name)));
			return;
		}

		if (!modifiersMatches(setter.modifiers, modifiers, AccStatic, AccPublic, AccProtected, AccPrivate)) {
			sourceNode.getNodeFor(setter).addWarning(String.format("conflicted with the %s.", source));
			return;
		}
	}

	protected void supplementSuperInterface(Class<?> interfaceType) {

		if (typeNode == null) return;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		if (findSuperInterface(interfaceType, clazz.superInterfaces) != null) return;

		TypeReference typeReference = recursiveSetGeneratedBy(generateTypeReference(interfaceType.getName()));

		if (clazz.superInterfaces == null) {
			clazz.superInterfaces = new TypeReference[] { typeReference };
		} else {
			clazz.superInterfaces = Arrays.copyOf(clazz.superInterfaces, clazz.superInterfaces.length + 1);
			clazz.superInterfaces[clazz.superInterfaces.length - 1] = typeReference;
		}
	}

	protected void supplementSuppressWaring(String suppressed) {

		if (typeNode == null) return;

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		char[] token = suppressed.toCharArray();

		SingleMemberAnnotation suppressWarnings = (SingleMemberAnnotation) findAnnotation(SuppressWarnings.class,
				clazz.annotations);

		if (suppressWarnings == null) {

			suppressWarnings = new SingleMemberAnnotation(generateTypeReference(SuppressWarnings.class.getName()), source.sourceStart);
			suppressWarnings.memberValue = new StringLiteral(token, source.sourceStart, source.sourceEnd, 0);
			recursiveSetGeneratedBy(suppressWarnings);

			if (Conditions.isEmpty(clazz.annotations)) {
				clazz.annotations = new org.eclipse.jdt.internal.compiler.ast.Annotation[] { suppressWarnings };
			} else {
				clazz.annotations = Arrays.copyOf(clazz.annotations, clazz.annotations.length + 1);
				clazz.annotations[clazz.annotations.length - 1] = suppressWarnings;
			}
			return;
		}

		if (suppressWarnings.memberValue instanceof StringLiteral) {
			StringLiteral value = (StringLiteral) suppressWarnings.memberValue;
			if (Arrays.equals(((StringLiteral) suppressWarnings.memberValue).source(), token)) return;

			StringLiteral serial = new StringLiteral(token, source.sourceStart, source.sourceEnd, 0);
			ArrayInitializer memberValue = new ArrayInitializer();
			memberValue.expressions = new Expression[] { value, serial };
			suppressWarnings.memberValue = recursiveSetGeneratedBy(memberValue);
			return;
		}

		if (suppressWarnings.memberValue instanceof ArrayInitializer) {
			ArrayInitializer values = (ArrayInitializer) suppressWarnings.memberValue;
			for (int i = 0; i < values.expressions.length; i++) {
				StringLiteral value = (StringLiteral) values.expressions[i];
				if (Arrays.equals(value.source(), token)) return;
			}

			values.expressions = Arrays.copyOf(values.expressions, values.expressions.length + 1);
			StringLiteral serial = recursiveSetGeneratedBy(new StringLiteral(token, source.sourceStart, source.sourceEnd, 0));
			values.expressions[values.expressions.length - 1] = serial;
		}
	}

	protected void supplementEqualsAndHashCode(boolean evaluateSuperclass) {

		if (typeNode == null) return;

		List<String> excludes = findExcludeFields(Equatable.class);
		List<String> includes = null;
		List<Annotation> onParam = Collections.emptyList();

		new HandleEqualsAndHashCode().generateMethods(typeNode, sourceNode, excludes, includes, evaluateSuperclass, false,
				FieldAccess.ALWAYS_FIELD, onParam);
	}

	protected void supplementToString(boolean evaluateSuperclass) {

		if (typeNode == null) return;

		List<String> excludes = findExcludeFields(Stringable.class);
		List<String> includes = null;

		new HandleToString().generateToString(typeNode, sourceNode, excludes, includes, true, evaluateSuperclass, false,
				FieldAccess.ALWAYS_FIELD);
	}

	protected void injectMethod(AbstractMethodDeclaration method) {

		if (typeNode == null) return;

		EclipseHandlerUtil.injectMethod(typeNode, method);

		typeNode.rebuild();
	}
}
