package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Negative;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class NegativeHandler extends AbstractNumericalAssertionHandler<Negative> {

	private static final char[] CHECK_METHOD_NAME = "checkNegative".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkNegativeIfNonnull".toCharArray();

	public NegativeHandler() {
		super(Negative.class);
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {

		if (!super.checkVariableType(variable)) return false;

		switch (variable.type.getLastToken()[0]) {
			case 'c': // char
			case 'C': // Character
				sourceNode.addWarning(String.format("@%s does not support %s type.", getAnnotationName(),
						String.valueOf(variable.type.getLastToken())));
				return false;
			default:
				return true;
		}
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}
}
