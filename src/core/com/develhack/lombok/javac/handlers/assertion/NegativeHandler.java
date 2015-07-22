package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Negative;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
public class NegativeHandler extends AbstractNumericalAssertionHandler<Negative> {

	public NegativeHandler() {
		super(Negative.class);
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {

		if (!super.checkVariableType(variable)) return false;

		switch (variable.vartype.toString().charAt(0)) {
			case 'c': // char
			case 'C': // Character
				sourceNode.addWarning(String.format("@%s does not support %s type.", getAnnotationName(),
						variable.vartype.toString()));
				return false;
			default:
				return true;
		}
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkNegativeIfNonnull" : "checkNegative";
	}
}
