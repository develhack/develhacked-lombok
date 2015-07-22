package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonzero;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class NonzeroHandler extends AbstractNumericalAssertionHandler<Nonzero> {

	private static final char[] CHECK_METHOD_NAME = "checkNonzero".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkNonzeroIfNonnull".toCharArray();

	public NonzeroHandler() {
		super(Nonzero.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}
}
