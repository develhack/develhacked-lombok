package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Utility;
import com.develhack.lombok.eclipse.handlers.feature.VOHandler;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(UtilityHandler.PRIORITY)
public class UtilityHandler extends AbstractFeatureHandler<Utility> {

	public static final int PRIORITY = VOHandler.PRIORITY + 1;

	public UtilityHandler() {
		super(Utility.class);
	}

	@Override
	public void handle(AnnotationValues<Utility> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		supplementFinalModifier();
		supplementUncallableConstructor();
	}
}
