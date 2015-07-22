package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonempty;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class NonemptyHandler extends AbstractAssertionHandler<Nonempty> {

	private static final char[] CHECK_METHOD_NAME = "checkNonempty".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkNonemptyIfNonnull".toCharArray();

	public NonemptyHandler() {
		super(Nonempty.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {
		return checkReferenceType(variable);
	}
}
