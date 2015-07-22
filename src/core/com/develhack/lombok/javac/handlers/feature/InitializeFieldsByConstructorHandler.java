package com.develhack.lombok.javac.handlers.feature;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;

import org.mangosdk.spi.ProviderFor;

import com.develhack.Conditions;
import com.develhack.annotation.feature.Access;
import com.develhack.annotation.feature.InitializeFieldsByConstructor;
import com.develhack.lombok.eclipse.handlers.feature.StringableHandler;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(InitializeFieldsByConstructorHandler.PRIORITY)
public class InitializeFieldsByConstructorHandler extends AbstractFeatureHandler<InitializeFieldsByConstructor> {

	public static final int PRIORITY = StringableHandler.PRIORITY + 1;

	public InitializeFieldsByConstructorHandler() {
		super(InitializeFieldsByConstructor.class);
	}

	@Override
	public void handle(AnnotationValues<InitializeFieldsByConstructor> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		super.handle(annotationValues, ast, annotationNode);

		JavacNode typeNode = annotationNode.up();

		if (typeNode.getKind() != Kind.TYPE) {
			annotationNode.addWarning(String.format("@%s is only applicable to the type.", getAnnotationName()));
			return;
		}

		InitializeFieldsByConstructor ifbc;
		try {
			ifbc = annotationValues.getInstance();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		if (ifbc.allFieldsInitializer() != Access.NONE) {
			List<JCVariableDecl> allInstanceFields = findFields(0, Flags.STATIC | Flags.TRANSIENT,
					InitializeFieldsByConstructor.class, true);
			if (Conditions.isEmpty(allInstanceFields)) return;

			supplementConstructor(ifbc.allFieldsInitializer(), allInstanceFields);
		}

		if (ifbc.finalFieldsInitializer() != Access.NONE) {
			List<JCVariableDecl> finalInstanceFields = findFields(Flags.FINAL, Flags.STATIC | Flags.TRANSIENT,
					InitializeFieldsByConstructor.class, true);
			if (Conditions.isEmpty(finalInstanceFields)) return;

			supplementConstructor(ifbc.finalFieldsInitializer(), finalInstanceFields);
		}

	}
}
