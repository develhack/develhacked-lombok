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

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(AccessibleHandler.PRIORITY)
public class AccessibleHandler extends AbstractFeatureHandler<Accessible> {

	public static final int PRIORITY = AbstractFeatureHandler.PRIORITY + 1;

	public AccessibleHandler() {
		super(Accessible.class);
	}

	@Override
	public void handle(AnnotationValues<Accessible> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode variableNode = annotationNode.up();

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

		FieldDeclaration field = (FieldDeclaration) annotationNode.up().get();
//		if (isTransient(field)) {
//			annotationNode.addWarning(String.format("@%s can not apply to the transient field.", getAnnotationName()));
//			return;
//		}

		for (EclipseNode fieldNode : annotationNode.upFromAnnotationToFields()) {
			field = (FieldDeclaration) fieldNode.get();
			supplementGetter(field, getAccess);
			supplementSetter(field, setAccess);
		}
	}
}
