package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonnull;
import com.develhack.annotation.assertion.Nullable;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class NonnullHandler extends AbstractAssertionHandler<Nonnull> {

	private static final char[] CHECK_METHOD_NAME = "checkNonnull".toCharArray();

	public NonnullHandler() {
		super(Nonnull.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return CHECK_METHOD_NAME;
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {

		if (!checkReferenceType(variable)) return false;

		if(findAnnotation(Nullable.class, variable.annotations) != null) {
			sourceNode.addWarning("conflicted with the @Nonnull.");
			return false;
		}

		return true;
	}
}
