package com.develhack.lombok.eclipse.handlers.assertion;

import java.util.HashMap;
import java.util.Map;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.InRange;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class InRangeHandler extends AbstractThresholdAssertionHandler<InRange> {

	private static final char[] CHECK_METHOD_NAME = "checkInRange".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkInRangeIfNonnull".toCharArray();

	public InRangeHandler() {
		super(InRange.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
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
