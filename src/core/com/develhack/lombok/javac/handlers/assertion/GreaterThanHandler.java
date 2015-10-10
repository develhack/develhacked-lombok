package com.develhack.lombok.javac.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.GreaterThan;

@ProviderFor(JavacAnnotationHandler.class)
public class GreaterThanHandler extends AbstractThresholdAssertionHandler<GreaterThan> {

	public GreaterThanHandler() {
		super(GreaterThan.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkGreaterThanIfNonnull" : "checkGreaterThan";
	}

	@Override
	protected Map<String, String> getAdditionalConditionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
