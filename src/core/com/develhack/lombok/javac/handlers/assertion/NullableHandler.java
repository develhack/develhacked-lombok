package com.develhack.lombok.javac.handlers.assertion;

import lombok.core.HandlerPriority;
import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nullable;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@ProviderFor(JavacAnnotationHandler.class)
@HandlerPriority(-1)
public class NullableHandler extends AbstractAssertionHandler<Nullable> {

	public NullableHandler() {
		super(Nullable.class);
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {
		return checkReferenceType(variable);
	}

	@Override
	protected String getCheckMethodName() {
		return null;
	}

	@Override
	protected boolean hasCheckMethodCall(JCMethodDecl method, JCVariableDecl variable) {
		return true;
	}
}
