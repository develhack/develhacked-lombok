package com.develhack.lombok.eclipse.handlers.assertion;

import lombok.core.HandlerPriority;
import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.Nullable;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
@HandlerPriority(-1)
public class NullableHandler extends AbstractAssertionHandler<Nullable> {

	public NullableHandler() {
		super(Nullable.class);
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {
		return checkReferenceType(variable);
	}

	@Override
	protected char[] getCheckMethodName() {
		return null;
	}

	@Override
	protected boolean hasCheckMethodCall(AbstractMethodDeclaration method, AbstractVariableDeclaration variable) {
		return true;
	}
}
