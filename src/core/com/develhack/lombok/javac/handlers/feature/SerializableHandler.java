package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Serializable;
import com.develhack.lombok.eclipse.handlers.feature.InitializeFieldsByConstructorHandler;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(SerializableHandler.PRIORITY)
public class SerializableHandler extends AbstractFeatureHandler<Serializable> {

	public static final int PRIORITY = InitializeFieldsByConstructorHandler.PRIORITY + 1;

	public SerializableHandler() {
		super(Serializable.class);
	}

	@Override
	public void handle(AnnotationValues<Serializable> annotation, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotation, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		supplementSuperInterface(java.io.Serializable.class);
		supplementSuppressWaring("serial");
	}
}
