package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Equatable;
import com.develhack.lombok.eclipse.handlers.feature.DTOHandler;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(EquatableHandler.PRIORITY)
public class EquatableHandler extends AbstractFeatureHandler<Equatable> {

	public static final int PRIORITY = DTOHandler.PRIORITY + 1;

	public EquatableHandler() {
		super(Equatable.class);
	}

	@Override
	public void handle(AnnotationValues<Equatable> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		Equatable equatable;
		try {
			equatable = annotationValues.getInstance();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		supplementEqualsAndHashCode(equatable.evaluateSuperclass());
	}
}
