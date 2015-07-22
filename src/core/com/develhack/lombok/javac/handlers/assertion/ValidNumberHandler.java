package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.ValidNumber;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
public class ValidNumberHandler extends AbstractAssertionHandler<ValidNumber> {

	public ValidNumberHandler() {
		super(ValidNumber.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkValidNumberIfNonnull" : "checkValidNumber";
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {
		return checkRealType(variable);
	}
}
