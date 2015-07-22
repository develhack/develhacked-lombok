package com.develhack.lombok.eclipse.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Serializable;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(SerializableHandler.PRIORITY)
public class SerializableHandler extends AbstractFeatureHandler<Serializable> {

	public static final int PRIORITY = InitializeFieldsByConstructorHandler.PRIORITY + 1;

	public SerializableHandler() {
		super(Serializable.class);
	}

	@Override
	public void handle(AnnotationValues<Serializable> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		supplementSuperInterface(java.io.Serializable.class);
		supplementSuppressWaring("serial");
	}
}
