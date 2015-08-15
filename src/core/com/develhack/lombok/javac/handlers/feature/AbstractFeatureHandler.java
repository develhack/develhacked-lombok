package com.develhack.lombok.javac.handlers.feature;

import static com.sun.tools.javac.code.Flags.*;
import static lombok.javac.Javac.*;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import lombok.ConfigurationKeys;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.javac.Javac;
import lombok.javac.JavacNode;
import lombok.javac.handlers.HandleEqualsAndHashCode;
import lombok.javac.handlers.HandleToString;
import lombok.javac.handlers.JavacHandlerUtil;
import lombok.javac.handlers.JavacHandlerUtil.FieldAccess;

import com.develhack.Conditions;
import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Equatable;
import com.develhack.annotation.feature.ExcludedFrom;
import com.develhack.annotation.feature.Meta;
import com.develhack.annotation.feature.Stringable;
import com.develhack.lombok.NameResolver;
import com.develhack.lombok.javac.handlers.AbstractJavacHandler;
import com.sun.source.tree.Tree.Kind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.parser.Tokens.Comment;
import com.sun.tools.javac.tree.DocCommentTable;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

public abstract class AbstractFeatureHandler<T extends Annotation> extends AbstractJavacHandler<T> {

	public static final int PRIORITY = Integer.MIN_VALUE;

	public AbstractFeatureHandler(Class<T> annotationType) {
		super(annotationType);
	}

	protected int toModifier(Access access) {
		switch (access) {
			case DEFAULT:
				return 0;
			case PUBLIC:
				return PUBLIC;
			case PROTECTED:
				return PROTECTED;
			case PRIVATE:
				return PRIVATE;
			default:
				throw new AssertionError(access.toString());
		}
	}

	protected boolean argumentTypesEquals(JCMethodDecl method, List<JCVariableDecl> arguments) {

		if (Conditions.isEmpty(method.getParameters())) {
			if (!Conditions.isEmpty(arguments)) return false;
			return true;
		}

		if (Conditions.isEmpty(arguments)) return false;
		if (method.getParameters().size() != arguments.size()) return false;

		Iterator<JCVariableDecl> argumentItr = method.getParameters().iterator();
		for (JCVariableDecl argument : arguments) {
			if (!argument.vartype.toString().equals(argumentItr.next().vartype.toString())) {
				return false;
			}
		}
		return true;
	}

	protected boolean isExcludedFrom(JCVariableDecl field, Class<? extends java.lang.annotation.Annotation> feature) {

		AnnotationValues<ExcludedFrom> excludeFrom = findAnnotationValues(ExcludedFrom.class, field.mods.annotations);
		if (excludeFrom == null) return false;

		try {
			for (Class<? extends java.lang.annotation.Annotation> specifiedFeature : excludeFrom.getInstance().value()) {
				if (feature.equals(specifiedFeature)) return true;
			}
		} catch (AnnotationValueDecodeFail e) {}

		return false;
	}

	protected JCMethodDecl findConstructor(List<JCVariableDecl> fieldsToBeInitialize) {

		if (typeNode == null) return null;
		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		for (JCTree child : clazz.defs) {

			if (child.getKind() != Kind.METHOD) continue;

			JCMethodDecl method = (JCMethodDecl) child;
			if (!isConstructor(method)) continue;

			if (argumentTypesEquals(method, fieldsToBeInitialize)) return method;
		}

		return null;
	}

	protected List<JCVariableDecl> findFields(int requiredModifiers, int excludedModifiers,
			Class<? extends java.lang.annotation.Annotation> excludeFeature, boolean notInitializedOnly) {

		if (typeNode == null) return null;
		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		ListBuffer<JCVariableDecl> fields = new ListBuffer<JCVariableDecl>();
		for (JCTree child : clazz.defs) {

			if (child.getKind() != Kind.VARIABLE) continue;

			JCVariableDecl field = (JCVariableDecl) child;
			if ((field.getModifiers().flags & requiredModifiers) != requiredModifiers) continue;
			if ((field.getModifiers().flags & excludedModifiers) != 0) continue;
			if (notInitializedOnly && field.getInitializer() != null) continue;
			if (isExcludedFrom(field, excludeFeature)) continue;

			fields.append(field);
		}

		return fields.toList();
	}

	protected JCExpression findSuperInterface(Class<?> interfaceType, List<JCExpression> superInterfaces) {

		if (Conditions.isEmpty(superInterfaces)) return null;

		String interfaceName = interfaceType.getSimpleName();
		for (JCExpression superInterface : superInterfaces) {
			if (getLastToken(superInterface).equals(interfaceName)) return superInterface;
		}

		return null;
	}

	protected List<String> findExcludeFields(Class<? extends java.lang.annotation.Annotation> feature) {

		if (typeNode == null) return null;
		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		ArrayList<String> excludes = new ArrayList<String>();
		for (JCTree child : clazz.defs) {

			if (child.getKind() != com.sun.source.tree.Tree.Kind.VARIABLE) continue;

			JCVariableDecl field = (JCVariableDecl) child;

			if (isExcludedFrom(field, feature)) excludes.add(field.name.toString());
		}

		return List.from(excludes.toArray(new String[excludes.size()]));
	}

	protected JCMethodDecl generateConstructor(List<JCVariableDecl> fieldsToBeInitialize, int modifiers) {

		List<JCTypeParameter> typeParameters = List.nil();
		ListBuffer<JCVariableDecl> parameters = new ListBuffer<JCVariableDecl>();
		List<JCExpression> thrown = List.nil();
		ListBuffer<JCStatement> statements = new ListBuffer<JCStatement>();
		for (JCVariableDecl fieldToBeInitialize : fieldsToBeInitialize) {

			String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), fieldToBeInitialize.name.toString());

			JCExpression argumentReference = maker.Ident(sourceNode.toName(argumentName));
			JCExpression fieldReference = generateFieldReference(fieldToBeInitialize);

			JCVariableDecl argument = maker.VarDef(maker.Modifiers(Flags.PARAMETER), sourceNode.toName(argumentName),
					fieldToBeInitialize.vartype, null);
			parameters.append(argument);

			JCAssign assignment = maker.Assign(fieldReference, argumentReference);
			statements.append(maker.Exec(assignment));
		}

		return maker.MethodDef(maker.Modifiers(modifiers), sourceNode.toName("<init>"), null, typeParameters,
				parameters.toList(), thrown, maker.Block(0, statements.toList()), null);
	}

	protected JCMethodDecl generateGetter(JCVariableDecl field, long modifiers) {

		String getterName = NameResolver.resolveGetterName(sourceNode.getAst(), field.name.toString(), isBoolean(field));

		JCExpression fieldReference = generateFieldReference(field);
		JCStatement returnStatement = maker.Return(fieldReference);

		JCModifiers mods = maker.Modifiers(modifiers, List.<JCAnnotation> nil());
		Name name = sourceNode.toName(getterName);
		JCExpression returnType = field.vartype;
		List<JCTypeParameter> typeParameters = List.nil();
		List<JCVariableDecl> parameters = List.nil();
		List<JCExpression> thrown = List.nil();
		JCBlock body = maker.Block(0, List.of(returnStatement));
		JCExpression defaultValue = null;

		return maker.MethodDef(mods, name, returnType, typeParameters, parameters, thrown, body, defaultValue);
	}

	protected JCMethodDecl generateSetter(JCVariableDecl field, long modifiers) {

		if (typeNode == null) return null;

		String setterName = NameResolver.resolveSetterName(sourceNode.getAst(), field.name.toString(), isBoolean(field));
		String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), field.name.toString());

		JCExpression argumentReference = maker.Ident(sourceNode.toName(argumentName));
		JCExpression fieldReference = generateFieldReference(field);

		JCAssign assignment = maker.Assign(fieldReference, argumentReference);

		JCVariableDecl argument = maker.VarDef(maker.Modifiers(Flags.PARAMETER), sourceNode.toName(argumentName),
				field.vartype, null);;

		JCModifiers mods = maker.Modifiers(modifiers | Flags.PARAMETER, List.<JCAnnotation> nil());
		Name name = sourceNode.toName(setterName);
		JCExpression returnType = maker.Type(Javac.createVoidType(maker, CTC_VOID));
		List<JCTypeParameter> typeParameters = List.nil();
		List<JCVariableDecl> parameters = List.of(argument);
		List<JCExpression> thrown = List.nil();
		JCBlock body = maker.Block(0, List.<JCStatement> of(maker.Exec(assignment)));
		JCExpression defaultValue = null;

		return maker.MethodDef(mods, name, returnType, typeParameters, parameters, thrown, body, defaultValue);
	}

	protected JCExpression generateFieldReference(JCVariableDecl field) {

		JCExpression fieldRef = null;
		if ((field.mods.flags & Flags.STATIC) != 0) {
			fieldRef = generateNameReference(typeNode.getName(), field.name.toString());
		} else {
			fieldRef = maker.Select(maker.Ident(sourceNode.toName("this")), field.name);
		}

		return fieldRef;
	}

	protected void supplementFinalModifier() {

		if (typeNode == null) return;

		JCClassDecl clazz = (JCClassDecl) typeNode.get();
		clazz.mods.flags |= FINAL;

		JavacNode modsNode = sourceNode.getNodeFor(clazz.mods);
		if (modsNode == null) return;

		modsNode.getAst().setChanged();
	}

	protected void supplementConstructor(Access access, List<JCVariableDecl> fieldsToBeInitialize) {

		if (typeNode == null) return;

		int modifiers = toModifier(access);

		JCMethodDecl constructor = findConstructor(fieldsToBeInitialize);
		if (constructor != null) {
			if (access == Access.NONE || (constructor.mods.flags & modifiers) != modifiers) {
				JavacNode constructorNode = sourceNode.getNodeFor(constructor);
				if (constructorNode != null) {
					constructorNode.addWarning(String.format("conflicted with the %s.", source));
				}
			}
			return;
		}

		constructor = generateConstructor(fieldsToBeInitialize, modifiers);

		injectMethod(recursiveSetGeneratedBy(constructor));

		StringBuilder constructorDocComment = new StringBuilder();

		String constructorDocCommentTemplate = sourceNode.getAst().readConfiguration(
				ConfigurationKeys.CONSTRUCTOR_COMMENT_TEMPLATE);
		if (constructorDocCommentTemplate != null) {
			JCClassDecl clazz = ((JCClassDecl) typeNode.get());
			Object[] docCommentArgs = buildJavadocCommentArgments(clazz, clazz.name.toString(), clazz.mods.annotations);
			constructorDocComment.append(MessageFormat.format(constructorDocCommentTemplate, docCommentArgs));
			constructorDocComment.append('\n');
		}
		for (JCVariableDecl fieldToBeInitialize : fieldsToBeInitialize) {
			String fieldComment = getJavadocComment(fieldToBeInitialize);
			if (fieldComment == null) continue;
			String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), fieldToBeInitialize.name.toString());
			constructorDocComment.append("@param ");
			constructorDocComment.append(argumentName);
			constructorDocComment.append(' ');
			constructorDocComment.append(fieldComment);
			constructorDocComment.append("\n");
		}
		if (constructorDocComment.length() != 0) {
			setJavadocComment(constructor, constructorDocComment.toString());
		}
	}

	protected void supplementUncallableConstructor() {

		if (typeNode == null) return;

		JavacNode defaultConstructorNode = null;
		for (JavacNode child : typeNode.down()) {
			if (child.getKind() != lombok.core.AST.Kind.METHOD) continue;
			JCMethodDecl method = (JCMethodDecl) child.get();
			if (!isConstructor(method)) continue;
			if ((method.mods.flags & GENERATEDCONSTR) != 0) {
				defaultConstructorNode = child;
				break;
			}
			if ((method.mods.flags & PRIVATE) == 0) {
				child.addWarning(String.format("class annotated by @%s must not have callable constructor.",
						getAnnotationName()));
				continue;
			}
		}

		if (defaultConstructorNode == null) {
			return;
		}

		JCExpression assertionError = maker.NewClass(null, List.<JCExpression> nil(),
				generateNameReference(AssertionError.class.getName()), List.<JCExpression> nil(), null);

		JCMethodDecl defaultConstructor = (JCMethodDecl) defaultConstructorNode.get();
		defaultConstructor.mods = maker.Modifiers(Flags.PRIVATE);
		defaultConstructor.typarams = List.nil();
		defaultConstructor.params = List.nil();
		defaultConstructor.thrown = List.nil();
		defaultConstructor.body = maker.Block(0, List.<JCStatement> of(maker.Throw(assertionError)));

		recursiveSetGeneratedBy(defaultConstructor);
	}

	protected void supplementGetter(JCVariableDecl field, Access access) {

		if (typeNode == null) return;

		JCMethodDecl getter = findGetter(field);
		if (access == Access.NONE) {
			if (getter != null) {
				sourceNode.getNodeFor(getter).addWarning(String.format("conflicted with the %s.", source));
			}
			return;
		}

		int modifiers = toModifier(access) | (int) (field.mods.flags & STATIC);

		if (getter == null) {
			getter = generateGetter(field, modifiers);
			if (getter == null) return;

			injectMethod(recursiveSetGeneratedBy(getter));

			String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), field.name.toString());
			Object[] docCommentArgs = buildJavadocCommentArgments(field, argumentName, field.mods.annotations);
			String getterDocCommentTemplate = sourceNode.getAst().readConfiguration(ConfigurationKeys.GETTER_COMMENT_TEMPLATE);
			StringBuilder getterDocComment = new StringBuilder();
			if (getterDocCommentTemplate != null) {
				getterDocComment.append(getterDocCommentTemplate);
				getterDocComment.append('\n');
			}
			if (docCommentArgs[1] != null) {
				getterDocComment.append("@return {1}");
			}
			if (getterDocComment.length() != 0) {
				setJavadocComment(getter, MessageFormat.format(getterDocComment.toString(), docCommentArgs));
			}
			return;
		}

		if (isAbstract(getter)) {
			sourceNode.addWarning(String.format("abstract getter of '%s' already exists.", field.name));
			return;
		}

		if (!modifiersMatches(getter.mods.flags, modifiers, STATIC, PUBLIC, PROTECTED, PRIVATE)) {
			sourceNode.getNodeFor(getter).addWarning(String.format("conflicted with the %s.", source));
			return;
		}
	}

	protected void supplementSetter(JCVariableDecl field, Access access) {

		if (typeNode == null) return;

		if ((field.mods.flags & FINAL) != 0) {
			return;
		}

		JCMethodDecl setter = findSetter(field);
		if (access == Access.NONE) {
			if (setter != null) {
				sourceNode.getNodeFor(setter).addWarning(String.format("conflicted with the %s.", source));
			}
			return;
		}

		int modifiers = toModifier(access) | (int) (field.mods.flags & STATIC);

		if (setter == null) {
			setter = generateSetter(field, modifiers);
			if (setter == null) return;

			injectMethod(recursiveSetGeneratedBy(setter));

			String argumentName = NameResolver.resolvePropertyName(sourceNode.getAst(), field.name.toString());
			Object[] docCommentArgs = buildJavadocCommentArgments(field, argumentName, field.mods.annotations);
			String setterDocCommentTemplate = sourceNode.getAst().readConfiguration(ConfigurationKeys.SETTER_COMMENT_TEMPLATE);
			StringBuilder setterDocComment = new StringBuilder();
			if (setterDocCommentTemplate != null) {
				setterDocComment.append(setterDocCommentTemplate);
				setterDocComment.append('\n');
			}
			if (docCommentArgs[1] != null) {
				setterDocComment.append("@param {0} {1}");
			}
			if (setterDocComment.length() != 0) {
				setJavadocComment(setter, MessageFormat.format(setterDocComment.toString(), docCommentArgs));
			}
			return;
		}

		if (isAbstract(setter)) {
			sourceNode.addWarning(String.format("abstract setter of '%s' already exists.", field.name));
			return;
		}

		if (!modifiersMatches(setter.mods.flags, modifiers, STATIC, PUBLIC, PROTECTED, PRIVATE)) {
			sourceNode.getNodeFor(setter).addWarning(String.format("conflicted with the %s.", source));
			return;
		}
	}

	protected void supplementSuperInterface(Class<?> interfaceType) {

		if (typeNode == null) return;

		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		if (findSuperInterface(interfaceType, clazz.implementing) != null) return;

		JCExpression typeReference = generateNameReference(interfaceType.getName());

		if (Conditions.isEmpty(clazz.implementing)) {
			clazz.implementing = List.of(typeReference);
		} else {
			clazz.implementing = clazz.implementing.append(typeReference);
		}
	}

	protected void supplementSuppressWaring(String suppressed) {

		if (typeNode == null) return;

		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		JCAnnotation suppressWarnings = findAnnotation(SuppressWarnings.class, clazz.mods.annotations);

		if (suppressWarnings == null) {

			suppressWarnings = maker.Annotation(generateNameReference(SuppressWarnings.class.getName()),
					List.<JCExpression> of(maker.Literal(suppressed)));
			recursiveSetGeneratedBy(suppressWarnings);

			if (Conditions.isEmpty(clazz.mods.annotations)) {
				clazz.mods.annotations = List.of(suppressWarnings);
			} else {
				clazz.mods.annotations = clazz.mods.annotations.append(suppressWarnings);
			}
			return;
		}

		for (JCExpression arg : suppressWarnings.args) {

			if (!(arg instanceof JCAssign)) continue;

			JCAssign assign = (JCAssign) arg;
			if (!assign.lhs.toString().equals("value")) continue;

			if (assign.rhs instanceof JCLiteral) {

				JCLiteral literal = (JCLiteral) assign.rhs;
				if (literal.value.equals(suppressed)) return;

				List<JCExpression> args = List.<JCExpression> of(maker.Literal(literal.value), maker.Literal(suppressed));
				assign.rhs = recursiveSetGeneratedBy(maker.NewArray(null, List.<JCExpression> nil(), args));
				return;
			}

			if (!(assign.rhs instanceof JCNewArray)) continue;

			JCNewArray values = (JCNewArray) assign.rhs;
			for (JCExpression value : values.elems) {

				if (!(value instanceof JCLiteral)) continue;

				JCLiteral literal = (JCLiteral) value;
				if (literal.value.equals(suppressed)) return;
			}

			values.elems = values.elems.append(recursiveSetGeneratedBy(maker.Literal(suppressed)));
			return;
		}
	}

	protected void supplementEqualsAndHashCode(boolean evaluateSuperclass) {

		if (typeNode == null) return;

		List<String> excludes = findExcludeFields(Equatable.class);
		List<String> includes = null;
		List<JCAnnotation> onParam = List.nil();

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

	protected void injectMethod(JCMethodDecl method) {

		if (typeNode == null) return;

		JavacHandlerUtil.injectMethod(typeNode, method);

		typeNode.rebuild();
	}

	// Javac only.
	protected JCTree getExtendsClause(JCClassDecl clazz) {
		return Javac.getExtendsClause(clazz);
	}

	@SuppressWarnings("unchecked")
	protected String getJavadocComment(JCTree tree) {

		JavacNode node = sourceNode.getNodeFor(tree);
		if (node == null) return null;

		try {
			JCCompilationUnit compilationUnit = ((JCCompilationUnit) node.top().get());
			Object docComments = Javac.getDocComments(compilationUnit);
			if (docComments instanceof Map) {
				return ((Map<JCTree, String>) docComments).get(tree);
			}
			if (Javac.instanceOfDocCommentTable(docComments)) {
				return Java8Comment.get(docComments, tree);
			}
		} catch (Exception e) {}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void setJavadocComment(final JCTree tree, final String docComment) {

		JavacNode node = sourceNode.getNodeFor(tree);
		if (node == null) return;

		try {
			JCCompilationUnit compilationUnit = ((JCCompilationUnit) node.top().get());
			Object docComments = Javac.getDocComments(compilationUnit);
			if (docComments instanceof Map) {
				((Map<JCTree, String>) docComments).put(tree, docComment);
			}
			if (Javac.instanceOfDocCommentTable(docComments)) {
				Java8Comment.set(docComments, tree, docComment);
			}
		} catch (Exception e) {}
	}

	private Object[] buildJavadocCommentArgments(JCTree tree, String name, List<JCAnnotation> annotations) {
		AnnotationValues<Meta> metaValues = findAnnotationValues(Meta.class, annotations);
		Object[] docCommentArgs;
		if (metaValues == null) {
			docCommentArgs = new Object[] { name, getJavadocComment(tree) };
		} else {
			String[] additionalArgs = metaValues.getInstance().value();
			docCommentArgs = new Object[2 + additionalArgs.length];
			docCommentArgs[0] = name;
			docCommentArgs[1] = getJavadocComment(tree);
			System.arraycopy(additionalArgs, 0, docCommentArgs, 2, additionalArgs.length);
		}
		return docCommentArgs;
	}

	private static class Java8Comment {

		static String get(Object docComments, JCTree tree) {
			return ((DocCommentTable) docComments).getCommentText(tree);
		}

		static void set(Object docComments, final JCTree tree, final String docComment) {
			((DocCommentTable) docComments).putComment(tree, new Comment() {

				@Override
				public String getText() {
					return docComment;
				}

				@Override
				public int getSourcePos(int index) {
					return -1;
				}

				@Override
				public CommentStyle getStyle() {
					return CommentStyle.JAVADOC;
				}

				@Override
				public boolean isDeprecated() {
					return JavacHandlerUtil.nodeHasDeprecatedFlag(tree);
				}
			});
		}
	}
}
