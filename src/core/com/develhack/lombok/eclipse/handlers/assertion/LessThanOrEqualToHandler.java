package com.develhack.lombok.eclipse.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.LessThanOrEqualTo;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class LessThanOrEqualToHandler extends AbstractThresholdAssertionHandler<LessThanOrEqualTo> {

	private static final char[] CHECK_METHOD_NAME = "checkLessThanOrEqualTo".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkLessThanOrEqualToIfNonnull".toCharArray();

	public LessThanOrEqualToHandler() {
		super(LessThanOrEqualTo.class);
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}

	@Override
	protected Map<String, String> getAdditionalConditionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
