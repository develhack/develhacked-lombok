package com.develhack.lombok.eclipse.handlers.feature;

import java.util.regex.Pattern;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Accessible;
import com.develhack.annotation.feature.VO;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(VOHandler.PRIORITY)
public class VOHandler extends AbstractFeatureHandler<VO> {

	public static final int PRIORITY = SerializableHandler.PRIORITY + 1;

	private static final Pattern OBJECT_PATTERN = Pattern.compile("^(?:java\\.lang\\.)?Object$");

	public VOHandler() {
		super(VO.class);
	}

	@Override
	public void handle(AnnotationValues<VO> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		TypeDeclaration clazz = (TypeDeclaration) typeNode.get();

		for (EclipseNode childe : typeNode.down()) {

			if (childe.getKind() != Kind.FIELD) continue;

			FieldDeclaration field = (FieldDeclaration) childe.get();
			if ((field.modifiers & ClassFileConstants.AccPrivate) == 0) {
				childe.addWarning(String.format("field of class that annotated by @%s must be private.", getAnnotationName()));
			}

			if (isExcludedFrom(field, VO.class)) continue;
			if (isTransient(field)) continue;

			Access getterAccess = Access.PUBLIC;
			Access setterAccess = Access.NONE;
			AnnotationValues<Accessible> accessibleValues = findAnnotationValues(Accessible.class, field.annotations);
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

		supplementFinalModifier();
		supplementSuperInterface(java.io.Serializable.class);
		supplementSuppressWaring("serial");
		supplementEqualsAndHashCode(clazz.superclass != null && !OBJECT_PATTERN.matcher(clazz.superclass.toString()).matches());
	}
}
