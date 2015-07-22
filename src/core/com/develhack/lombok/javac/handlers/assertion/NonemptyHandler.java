package com.develhack.lombok.javac.handlers.assertion;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nonempty;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
public class NonemptyHandler extends AbstractAssertionHandler<Nonempty> {

	public NonemptyHandler() {
		super(Nonempty.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkNonemptyIfNonnull" : "checkNonempty";
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {
		return checkReferenceType(variable);
	}
}
