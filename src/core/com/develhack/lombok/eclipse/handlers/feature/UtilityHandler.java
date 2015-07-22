package com.develhack.lombok.eclipse.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Utility;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(UtilityHandler.PRIORITY)
public class UtilityHandler extends AbstractFeatureHandler<Utility> {

	public static final int PRIORITY = VOHandler.PRIORITY + 1;

	public UtilityHandler() {
		super(Utility.class);
	}

	@Override
	public void handle(AnnotationValues<Utility> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		supplementFinalModifier();
		supplementUncallableConstructor();
	}
}
