package com.develhack.lombok.javac.handlers.assertion;

import java.lang.annotation.Annotation;

import com.develhack.lombok.NameResolver;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

abstract class AbstractNumericalAssertionHandler<T extends Annotation> extends AbstractAssertionHandler<T> {

	public AbstractNumericalAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {

		if (isPrimitiveNumber(variable) || isBoxedNumber(variable)) return true;

		String argumentTypeName = variable.vartype.toString();

		if (NameResolver.resolveFQN(sourceNode, argumentTypeName, NameResolver.BIG_INTEGER, NameResolver.BIG_DECIMAL) != null) {
			return true;
		}

		sourceNode.addWarning(String.format("@%s is only applicable to the numerical type.", getAnnotationName()));
		return false;
	}
}
