package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Stringable;
import com.develhack.lombok.eclipse.handlers.feature.EquatableHandler;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(StringableHandler.PRIORITY)
public class StringableHandler extends AbstractFeatureHandler<Stringable> {

	public static final int PRIORITY = EquatableHandler.PRIORITY + 1;

	public StringableHandler() {
		super(Stringable.class);
	}

	@Override
	public void handle(AnnotationValues<Stringable> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

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
