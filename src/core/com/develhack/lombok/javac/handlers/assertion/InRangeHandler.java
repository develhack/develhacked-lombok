package com.develhack.lombok.javac.handlers.assertion;

import java.util.HashMap;
import java.util.Map;

import lombok.javac.JavacAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.InRange;

@ProviderFor(JavacAnnotationHandler.class)
public class InRangeHandler extends AbstractThresholdAssertionHandler<InRange> {

	public InRangeHandler() {
		super(InRange.class);
	}

	@Override
	protected String getCheckMethodName() {
		return nullable ? "checkInRangeIfNonnull" : "checkInRange";
	}

	@Override
	protected Map<String, String> getAdditionalConditionMap() {
		InRange inRange = annotationValues.getInstance();
		Map<String, String> additionalCondtionMap = new HashMap<String, String>(2);
		additionalCondtionMap.put("from", inRange.from());
		additionalCondtionMap.put("to", inRange.to());
		return additionalCondtionMap;
	}
}
