package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.Accessible;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(AccessibleHandler.PRIORITY)
public class AccessibleHandler extends AbstractFeatureHandler<Accessible> {

	public static final int PRIORITY = AbstractFeatureHandler.PRIORITY + 1;

	public AccessibleHandler() {
		super(Accessible.class);
	}

	@Override
	public void handle(AnnotationValues<Accessible> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode variableNode = annotationNode.up();

		if (variableNode.getKind() != Kind.FIELD) {
			annotationNode.addWarning(String.format("@%s is only applicable to the field.", getAnnotationName()));
			return;
		}

		Accessible accessible;
		try {
			accessible = annotationValues.getInstance();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}
		Access getAccess = accessible.get();
		Access setAccess = accessible.set();

		JCVariableDecl field = (JCVariableDecl) annotationNode.up().get();
//		if (isTransient(field)) {
//			annotationNode.addWarning(String.format("@%s can not apply to the transient field.", getAnnotationName()));
//			return;
//		}

		for (JavacNode fieldNode : annotationNode.upFromAnnotationToFields()) {
			field = (JCVariableDecl) fieldNode.get();
			supplementGetter(field, getAccess);
			supplementSetter(field, setAccess);
		}
	}
}
