package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.FiniteNumber;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
public class FiniteNumberHandler extends AbstractAssertionHandler<FiniteNumber> {

	public FiniteNumberHandler() {
		super(FiniteNumber.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkFiniteNumberIfNonnull" : "checkFiniteNumber";
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {
		return checkRealType(variable);
	}
}
