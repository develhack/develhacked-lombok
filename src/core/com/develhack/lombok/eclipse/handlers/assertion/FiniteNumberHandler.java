package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.FiniteNumber;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class FiniteNumberHandler extends AbstractAssertionHandler<FiniteNumber> {

	private static final char[] CHECK_METHOD_NAME = "checkFiniteNumber".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkFiniteNumberIfNonnull".toCharArray();

	public FiniteNumberHandler() {
		super(FiniteNumber.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {
		return checkRealType(variable);
	}
}
