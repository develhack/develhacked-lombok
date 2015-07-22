package com.develhack.lombok.javac.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.GreaterThanOrEqualTo;

@ProviderFor(JavacAnnotationHandler.class)
public class GreaterThanOrEqualToHandler extends AbstractThresholdAssertionHandler<GreaterThanOrEqualTo> {

	public GreaterThanOrEqualToHandler() {
		super(GreaterThanOrEqualTo.class);
	}

	@Override
	public Class<GreaterThanOrEqualTo> getAnnotationHandledByThisHandler() {
		return GreaterThanOrEqualTo.class;
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkGreaterThanOrEqualToIfNonnull" : "checkGreaterThanOrEqualTo";
	}

	@Override
	protected Map<String, String> getAdditionalCondtionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
