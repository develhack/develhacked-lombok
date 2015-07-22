package com.develhack.lombok.eclipse.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Stringable;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(StringableHandler.PRIORITY)
public class StringableHandler extends AbstractFeatureHandler<Stringable> {

	public static final int PRIORITY = EquatableHandler.PRIORITY + 1;

	public StringableHandler() {
		super(Stringable.class);
	}

	@Override
	public void handle(AnnotationValues<Stringable> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		Stringable stringable;
		try {
			stringable = annotationValues.getInstance();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		supplementToString(stringable.evaluateSuperclass());
	}
}
