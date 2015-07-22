package com.develhack.lombok;

import java.util.List;

import lombok.ConfigurationKeys;
import lombok.core.AST;
import lombok.core.LombokNode;
import lombok.core.TypeLibrary;
import lombok.core.TypeResolver;
import lombok.core.handlers.HandlerUtil;

import com.develhack.Conditions;

public class NameResolver {

	public static final TypeLibrary BIG_INTEGER;
	public static final TypeLibrary BIG_DECIMAL;
	public static final TypeLibrary DATE;
	public static final TypeLibrary JDK_LOCAL_DATE;
	public static final TypeLibrary JDK_LOCAL_TIME;
	public static final TypeLibrary JDK_LOCAL_DATE_TIME;
	public static final TypeLibrary JDK_OFFSET_TIME;
	public static final TypeLibrary JDK_OFFSET_DATE_TIME;
	public static final TypeLibrary JDK_ZONED_DATE_TIME;
	public static final TypeLibrary BP_LOCAL_DATE;
	public static final TypeLibrary BP_LOCAL_TIME;
	public static final TypeLibrary BP_LOCAL_DATE_TIME;
	public static final TypeLibrary BP_OFFSET_TIME;
	public static final TypeLibrary BP_OFFSET_DATE_TIME;
	public static final TypeLibrary BP_ZONED_DATE_TIME;

	static {
		BIG_INTEGER = createTypeLibrary("java.math.BigInteger");
		BIG_DECIMAL = createTypeLibrary("java.math.BigDecimal");
		DATE = createTypeLibrary("java.util.Date");
		JDK_LOCAL_DATE = createTypeLibrary("java.time.LocalDate");
		JDK_LOCAL_TIME = createTypeLibrary("java.time.LocalTime");
		JDK_LOCAL_DATE_TIME = createTypeLibrary("java.time.LocalDateTime");
		JDK_OFFSET_TIME = createTypeLibrary("java.time.OffsetTime");
		JDK_OFFSET_DATE_TIME = createTypeLibrary("java.time.OffsetDateTime");
		JDK_ZONED_DATE_TIME = createTypeLibrary("java.time.ZonedDateTime");
		BP_LOCAL_DATE = createTypeLibrary("org.threeten.bp.LocalDate");
		BP_LOCAL_TIME = createTypeLibrary("org.threeten.bp.LocalTime");
		BP_LOCAL_DATE_TIME = createTypeLibrary("org.threeten.bp.LocalDateTime");
		BP_OFFSET_TIME = createTypeLibrary("org.threeten.bp.OffsetTime");
		BP_OFFSET_DATE_TIME = createTypeLibrary("org.threeten.bp.OffsetDateTime");
		BP_ZONED_DATE_TIME = createTypeLibrary("org.threeten.bp.ZonedDateTime");
	}

	private static TypeLibrary createTypeLibrary(String fqn) {
		TypeLibrary typeLibrary = new TypeLibrary();
		typeLibrary.addType(fqn);
		typeLibrary.lock();
		return typeLibrary;
	}

	public static String resolveFQN(LombokNode<?, ?, ?> context, String name, TypeLibrary... libraries) {

		TypeResolver resolver = new TypeResolver(context.getImportList());

		for (TypeLibrary library : libraries) {
			String fqn = resolver.typeRefToFullyQualifiedName(context, library, name);
			if (fqn != null) return fqn;
		}

		return null;
	}

	public static String resolvePropertyName(AST<?, ?, ?> ast, String fieldName) {
		if (fieldName.length() == 0) return null;
		List<String> prefix = ast.readConfiguration(ConfigurationKeys.ACCESSORS_PREFIX);
		CharSequence unprefixedFieldName = HandlerUtil.removePrefix(fieldName, prefix);
		if (unprefixedFieldName == null) return fieldName;
		return unprefixedFieldName.toString();
	}

	public static String resolveAccessorName(AST<?, ?, ?> ast, String fieldName, boolean isBoolean, String booleanPrefix,
			String normalPrefix, boolean adhereToFluent) {

		if (Boolean.TRUE.equals(ast.readConfiguration(ConfigurationKeys.GETTER_CONSEQUENT_BOOLEAN))) isBoolean = false;
		boolean fluent = Boolean.TRUE.equals(ast.readConfiguration(ConfigurationKeys.ACCESSORS_FLUENT));

		String fName = resolvePropertyName(ast, fieldName);
		if(fName == null) return null;
		if (adhereToFluent && fluent) return fName;

		if (isBoolean && fName.startsWith("is") && fieldName.length() > 2 && !Character.isLowerCase(fieldName.charAt(2))) {
			// The field is for example named 'isRunning'.
			return booleanPrefix + fName.substring(2);
		}

		return HandlerUtil.buildAccessorName(isBoolean ? booleanPrefix : normalPrefix, fName);
	}

	public static String resolveGetterName(AST<?, ?, ?> ast, String fieldName, boolean isBoolean) {
		return resolveAccessorName(ast, fieldName, isBoolean, "is", "get", true);
	}

	public static String resolveSetterName(AST<?, ?, ?> ast, String fieldName, boolean isBoolean) {
		return resolveAccessorName(ast, fieldName, isBoolean, "set", "set", true);
	}

	public static String resolveConstantFieldName(String typeFqn, String value, boolean isBoxedType) {

		StringBuilder buff = new StringBuilder().append('$');
		String[] tokens = typeFqn.split("\\.");
		if(tokens.length == 1 && isBoxedType) {
			buff.append("JAVA_LANG_");
		}
		for (String token : tokens) {
			buff.append(toSnakeCase(token, true));
			buff.append('_');
		}

		for (int i = 0, length = value.length(); i < length; i++) {
			char c = value.charAt(i);
			buff.append(Conditions.isInRange(c, '0', '9') ? c : '_');
		}

		return buff.toString();
	}

	public static String toSnakeCase(CharSequence camelCase, boolean upperCase) {

		if (camelCase == null) return null;
		if (camelCase.length() == 0) return "";

		int length = camelCase.length();
		StringBuilder builder = new StringBuilder(length * 2);

		boolean adjacent;
		char c = camelCase.charAt(0);
		if (Character.isUpperCase(c)) {
			builder.append(upperCase ? c : Character.toLowerCase(c));
			adjacent = true;
		} else {
			builder.append(upperCase ? Character.toUpperCase(c) : c);
			adjacent = false;
		}
		for (int i = 1; i < length; i++) {
			c = camelCase.charAt(i);
			if (Character.isUpperCase(c)) {
				if (!adjacent) builder.append('_');
				builder.append(upperCase ? c : Character.toLowerCase(c));
				adjacent = true;
			} else {
				builder.append(upperCase ? Character.toUpperCase(c) : c);
				adjacent = false;
			}
		}

		return builder.toString();
	}
}
