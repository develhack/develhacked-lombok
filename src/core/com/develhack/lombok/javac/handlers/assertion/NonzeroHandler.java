package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonzero;

@ProviderFor(JavacAnnotationHandler.class)
public class NonzeroHandler extends AbstractNumericalAssertionHandler<Nonzero> {

	public NonzeroHandler() {
		super(Nonzero.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkNonzeroIfNonnull" : "checkNonzero";
	}
}
