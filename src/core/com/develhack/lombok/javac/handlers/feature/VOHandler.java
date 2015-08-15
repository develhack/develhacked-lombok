package com.develhack.lombok.javac.handlers.feature;

import java.util.regex.Pattern;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Accessible;
import com.develhack.annotation.feature.VO;
import com.develhack.lombok.eclipse.handlers.feature.SerializableHandler;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(VOHandler.PRIORITY)
public class VOHandler extends AbstractFeatureHandler<VO> {

	public static final int PRIORITY = SerializableHandler.PRIORITY + 1;

	private static final Pattern OBJECT_PATTERN = Pattern.compile("^(?:java\\.lang\\.)?Object$");

	public VOHandler() {
		super(VO.class);
	}

	@Override
	public void handle(AnnotationValues<VO> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		JCClassDecl clazz = (JCClassDecl) typeNode.get();

		for (JavacNode childe : typeNode.down()) {

			if (childe.getKind() != Kind.FIELD) continue;

			JCVariableDecl field = (JCVariableDecl) childe.get();
			if ((field.mods.flags & Flags.PRIVATE) == 0) {
				childe.addWarning(String.format("field of class that annotated by @%s must be private.", getAnnotationName()));
			}

			if (isExcludedFrom(field, VO.class)) continue;
			if (isTransient(field)) continue;

			Access getterAccess = Access.PUBLIC;
			Access setterAccess = Access.NONE;
			AnnotationValues<Accessible> accessibleValues = findAnnotationValues(Accessible.class, field.mods.annotations);
			if (accessibleValues != null) {
				try {
					Accessible accessible = accessibleValues.getInstance();
					getterAccess = accessible.get();
					if (accessibleValues.getRawExpression("set") != null) {
						setterAccess = accessible.set();
					}
					if (setterAccess.ordinal() < Access.PRIVATE.ordinal()) {
						accessibleValues.setWarning("set",
								String.format("class annotated @%s must have no accessible setters.", getAnnotationName()));
					}
				} catch (AnnotationValueDecodeFail e) {
					continue;
				}
			}
			supplementGetter(field, getterAccess);
			supplementSetter(field, setterAccess);
		}

		JCTree extendsClause = getExtendsClause(clazz);
		supplementSuperInterface(java.io.Serializable.class);
		supplementSuppressWaring("serial");
		supplementEqualsAndHashCode(extendsClause != null && !OBJECT_PATTERN.matcher(extendsClause.toString()).matches());
	}
}
