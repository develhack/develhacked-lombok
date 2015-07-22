package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonnull;
import com.develhack.annotation.assertion.Nullable;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
public class NonnullHandler extends AbstractAssertionHandler<Nonnull> {

	public NonnullHandler() {
		super(Nonnull.class);
	}

	@Override
	protected String getCheckMethodName() {
		return "checkNonnull";
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {

		if (!checkReferenceType(variable)) return false;

		if(findAnnotation(Nullable.class, variable.mods.annotations) != null) {
			sourceNode.addWarning("conflicted with the @Nonnull.");
			return false;
		}

		return true;
	}
}
