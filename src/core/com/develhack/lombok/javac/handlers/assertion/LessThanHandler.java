package com.develhack.lombok.javac.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.LessThan;

@ProviderFor(JavacAnnotationHandler.class)
public class LessThanHandler extends AbstractThresholdAssertionHandler<LessThan> {

	public LessThanHandler() {
		super(LessThan.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkLessThanIfNonnull" : "checkLessThan";
	}

	@Override
	protected Map<String, String> getAdditionalConditionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
