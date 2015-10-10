package com.develhack.lombok.eclipse.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.GreaterThan;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class GreaterThanHandler extends AbstractThresholdAssertionHandler<GreaterThan> {

	private static final char[] CHECK_METHOD_NAME = "checkGreaterThan".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkGreaterThanIfNonnull".toCharArray();

	public GreaterThanHandler() {
		super(GreaterThan.class);
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
