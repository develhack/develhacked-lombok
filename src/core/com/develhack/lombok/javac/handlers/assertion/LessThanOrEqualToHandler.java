package com.develhack.lombok.javac.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.LessThanOrEqualTo;

@ProviderFor(JavacAnnotationHandler.class)
public class LessThanOrEqualToHandler extends AbstractThresholdAssertionHandler<LessThanOrEqualTo> {

	public LessThanOrEqualToHandler() {
		super(LessThanOrEqualTo.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkLessThanOrEqualToIfNonnull" : "checkLessThanOrEqualTo";
	}

	@Override
	protected Map<String, String> getAdditionalCondtionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
