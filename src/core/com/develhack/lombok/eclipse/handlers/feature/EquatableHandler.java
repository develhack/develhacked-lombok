package com.develhack.lombok.eclipse.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Equatable;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(EquatableHandler.PRIORITY)
public class EquatableHandler extends AbstractFeatureHandler<Equatable> {

	public static final int PRIORITY = DTOHandler.PRIORITY + 1;

	public EquatableHandler() {
		super(Equatable.class);
	}

	@Override
	public void handle(AnnotationValues<Equatable> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		Equatable equatable;
		try {
			equatable = annotationValues.getInstance();
		} catch(AnnotationValueDecodeFail e) {
			return;
		}

		supplementEqualsAndHashCode(equatable.evaluateSuperclass());
	}
}
