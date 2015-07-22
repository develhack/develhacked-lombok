package com.develhack.lombok.eclipse.handlers.feature;

import static org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.*;
import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.Conditions;
import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.InitializeFieldsByConstructor;

@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(InitializeFieldsByConstructorHandler.PRIORITY)
public class InitializeFieldsByConstructorHandler extends AbstractFeatureHandler<InitializeFieldsByConstructor> {

	public static final int PRIORITY = StringableHandler.PRIORITY + 1;

	public InitializeFieldsByConstructorHandler() {
		super(InitializeFieldsByConstructor.class);
	}

	@Override
	public void handle(AnnotationValues<InitializeFieldsByConstructor> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		EclipseNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		InitializeFieldsByConstructor ifbc;
		try {
			ifbc = annotationValues.getInstance();
		} catch(AnnotationValueDecodeFail e) {
			return;
		}

		if (ifbc.allFieldsInitializer() != Access.NONE) {
			FieldDeclaration[] allInstanceFields = findFields(0, AccStatic | AccTransient, InitializeFieldsByConstructor.class, true);
			if (Conditions.isEmpty(allInstanceFields)) return;

			supplementConstructor(ifbc.allFieldsInitializer(), allInstanceFields);
		}

		if (ifbc.finalFieldsInitializer() != Access.NONE) {
			FieldDeclaration[] finalInstanceFields = findFields(AccFinal, AccStatic | AccTransient, InitializeFieldsByConstructor.class, true);
			if (Conditions.isEmpty(finalInstanceFields)) return;

			supplementConstructor(ifbc.finalFieldsInitializer(), finalInstanceFields);
		}

	}
}
