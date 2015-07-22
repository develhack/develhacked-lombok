package com.develhack.lombok.eclipse.handlers.assertion;

import static lombok.eclipse.Eclipse.toQualifiedName;

import java.lang.annotation.Annotation;

import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;

import com.develhack.lombok.NameResolver;

abstract class AbstractNumericalAssertionHandler<T extends Annotation> extends AbstractAssertionHandler<T> {

	public AbstractNumericalAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {

		if (isPrimitiveNumber(variable) || isBoxedNumber(variable)) return true;

		String argumentTypeName = toQualifiedName(variable.type.getTypeName());

		if (NameResolver.resolveFQN(sourceNode, argumentTypeName, NameResolver.BIG_INTEGER, NameResolver.BIG_DECIMAL) != null) {
			return true;
		}

		sourceNode.addWarning(String.format("@%s is only applicable to the numerical type.", getAnnotationName()));
		return false;
	}
}
