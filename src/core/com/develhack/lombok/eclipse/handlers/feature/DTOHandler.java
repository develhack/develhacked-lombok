package com.develhack.lombok.eclipse.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Accessible;
import com.develhack.annotation.feature.DTO;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(DTOHandler.PRIORITY)
public class DTOHandler extends AbstractFeatureHandler<DTO> {

	public static final int PRIORITY = AccessibleHandler.PRIORITY + 1;

	public DTOHandler() {
		super(DTO.class);
	}

	@Override
	public void handle(AnnotationValues<DTO> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		DTO dto;
		try {
			dto = annotationValues.getInstance();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		for (EclipseNode childe : typeNode.down()) {

			if (childe.getKind() != Kind.FIELD) continue;

			FieldDeclaration field = (FieldDeclaration) childe.get();

			if (isExcludedFrom(field, DTO.class)) continue;
			if (isTransient(field)) continue;

			AnnotationValues<Accessible> accessibleValues = findAnnotationValues(Accessible.class, field.annotations);

			if (accessibleValues != null) {
				Accessible accessible;
				try {
					accessible = accessibleValues.getInstance();
				} catch (AnnotationValueDecodeFail e) {
					continue;
				}
				Access getAccess = accessible.get();
				Access setAccess = accessible.set();

				supplementGetter(field, getAccess);
				supplementSetter(field, setAccess);

			} else {

				supplementGetter(field, dto.get());
				supplementSetter(field, dto.set());
			}
		}

		supplementSuperInterface(java.io.Serializable.class);
		supplementSuppressWaring("serial");
	}
}
