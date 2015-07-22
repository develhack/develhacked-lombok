package com.develhack.lombok.eclipse.handlers.assertion;

import java.util.Collections;
import java.util.Map;

import lombok.eclipse.DeferUntilPostDiet;
import lombok.eclipse.EclipseAnnotationHandler;

import org.mangosdk.spi.ProviderFor;

import com.develhack.annotation.assertion.GreaterThanOrEqualTo;

@DeferUntilPostDiet
@ProviderFor(EclipseAnnotationHandler.class)
public class GreaterThanOrEqualToHandler extends AbstractThresholdAssertionHandler<GreaterThanOrEqualTo> {

	private static final char[] CHECK_METHOD_NAME = "checkGreaterThanOrEqualTo".toCharArray();
	private static final char[] CHECK_IF_NONNULL_METHOD_NAME = "checkGreaterThanOrEqualToIfNonnull".toCharArray();

	public GreaterThanOrEqualToHandler() {
		super(GreaterThanOrEqualTo.class);
	}

	@Override
	public Class<GreaterThanOrEqualTo> getAnnotationHandledByThisHandler() {
		return GreaterThanOrEqualTo.class;
	}

	@Override
	protected char[] getCheckMethodName() {
		return nullable ? CHECK_IF_NONNULL_METHOD_NAME : CHECK_METHOD_NAME;
	}

	@Override
	protected Map<String, String> getAdditionalCondtionMap() {
		return Collections.singletonMap("value", annotationValues.getInstance().value());
	}
}
