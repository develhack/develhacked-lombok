package com.develhack.lombok.eclipse.handlers.assertion;

import static com.develhack.lombok.NameResolver.*;
import static lombok.eclipse.Eclipse.*;
import static lombok.eclipse.handlers.EclipseHandlerUtil.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.eclipse.EclipseNode;
import lombok.eclipse.handlers.EclipseHandlerUtil.MemberExistsResult;

import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.CastExpression;
import org.eclipse.jdt.internal.compiler.ast.DoubleLiteral;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FloatLiteral;
import org.eclipse.jdt.internal.compiler.ast.IntLiteral;
import org.eclipse.jdt.internal.compiler.ast.LongLiteral;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.OperatorIds;
import org.eclipse.jdt.internal.compiler.ast.StringLiteral;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.UnaryExpression;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.OffsetTime;
import org.threeten.bp.ZonedDateTime;

import com.develhack.lombok.NameResolver;

abstract class AbstractThresholdAssertionHandler<T extends java.lang.annotation.Annotation> extends AbstractAssertionHandler<T> {

	private static final Pattern DATE_TIME_PATTERN = Pattern
			.compile("\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?)?");

	public AbstractThresholdAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	public void preHandle(AnnotationValues<T> annotationValues, Annotation ast, EclipseNode annotationNode) {

		super.preHandle(annotationValues, ast, annotationNode);

		EclipseNode variableNode = annotationNode.up();
		AbstractVariableDeclaration variable = (AbstractVariableDeclaration) variableNode.get();

		if (!checkVariableType(variable)) return;

		switch (variableNode.getKind()) {

			case FIELD:
				for (EclipseNode fieldNode : annotationNode.upFromAnnotationToFields()) {
					AbstractVariableDeclaration field = (AbstractVariableDeclaration) fieldNode.get();
					preProcess(field);
				}
				return;

			case ARGUMENT:
				AbstractMethodDeclaration method = (AbstractMethodDeclaration) variableNode.up().get();
				preProcess(variable);
				processArgument(method, (Argument) variable);
				return;

			default:
				return;
		}
	}

	@Override
	protected boolean checkVariableType(AbstractVariableDeclaration variable) {

		Map<String, String> additionalCondtionMap;
		try {
			additionalCondtionMap = getAdditionalCondtionMap();
		} catch (AnnotationValueDecodeFail e) {
			return false;
		}

		for (Entry<String, String> additionalCondtion : additionalCondtionMap.entrySet()) {
			String representation = additionalCondtion.getValue();
			if (representation.isEmpty()) {
				annotationValues.setError(additionalCondtion.getKey(), "must specify the value.");
				return false;
			}
		}

		return true;
	}

	protected void preProcess(AbstractVariableDeclaration variable) {

		if (typeNode == null) return;

		Map<String, String> additionalCondtionMap;
		try {
			additionalCondtionMap = getAdditionalCondtionMap();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		EclipseNode topTypeNode = resolveTopTypeNode();

		for (Entry<String, String> additionalCondition : additionalCondtionMap.entrySet()) {

			String representation = additionalCondition.getValue();
			if (representation.isEmpty()) continue;

			if (!isLiteral(representation)) continue;

			String fieldName = NameResolver.resolveConstantFieldName(toQualifiedName(variable.type.getTypeName()),
					representation, isBoxedType(variable));
			if (fieldExists(fieldName, topTypeNode) != MemberExistsResult.NOT_EXISTS) continue;

			Expression initialization;

			try {

				if (isPrimitiveNumber(variable) || isBoxedNumber(variable)) {

					initialization = generatePrimitiveInitializer(representation, variable.type);

				} else {

					initialization = generateReferenceTypeInitializer(representation, variable.type);
				}

				if (initialization == null) {
					sourceNode.addWarning(String.format("@%s does not support the %s.", getAnnotationName(),
							variable.type.toString()));
					return;
				}

			} catch (Exception e) {

				annotationValues.setError(additionalCondition.getKey(),
						String.format("cannot parse as %s.", variable.type.toString()));

				continue;
			}

			Annotation annotation = (Annotation) source;

			FieldDeclaration field = new FieldDeclaration(fieldName.toCharArray(), 0, -1);
			field.sourceStart = field.declarationSourceStart = 0;
			field.sourceEnd = field.declarationSourceEnd = field.declarationEnd = -1;
			field.type = copyType(variable.type, annotation);
			field.modifiers = (ClassFileConstants.AccPrivate | ClassFileConstants.AccStatic | ClassFileConstants.AccFinal);
			field.initialization = initialization;

			injectFieldAndMarkGenerated(topTypeNode, recursiveSetGeneratedBy(field));
		}
	}

	private Expression generatePrimitiveInitializer(String representation, TypeReference ref) {

		char[] token = representation.toCharArray();
		boolean minus = (token[0] == '-');
		if (minus) {
			token = Arrays.copyOfRange(token, 1, token.length);
		}

		Expression initialization;
		switch (ref.getLastToken()[0]) {
			case 'd':
			case 'D':
				Double.parseDouble(representation);
				initialization = new DoubleLiteral(token, pS, pE);
				break;
			case 'f':
			case 'F':
				Float.parseFloat(representation);
				initialization = new FloatLiteral(token, pS, pE);
				break;
			case 'l':
			case 'L':
				Long.parseLong(representation);
				initialization = LongLiteral.buildLongLiteral(token, pS, pE);
				break;
			case 'i':
			case 'I':
				Integer.parseInt(representation);
				initialization = IntLiteral.buildIntLiteral(token, pS, pE);
				break;
			case 's':
			case 'S':
				Short.parseShort(representation);
				initialization = new CastExpression(IntLiteral.buildIntLiteral(token, pS, pE), copyType(ref, source));
				break;
			case 'b':
			case 'B':
				Short.parseShort(representation);
				initialization = new CastExpression(IntLiteral.buildIntLiteral(token, pS, pE), copyType(ref, source));
				break;
			case 'c':
			case 'C':
				Integer.parseInt(representation);
				initialization = new CastExpression(IntLiteral.buildIntLiteral(token, pS, pE), copyType(ref, source));
				break;
			default:
				return null;
		}

		if (minus) initialization = new UnaryExpression(initialization, OperatorIds.MINUS);
		return initialization;
	}

	private Expression generateReferenceTypeInitializer(String representation, TypeReference ref) throws ParseException {

		char[] token = representation.toCharArray();

		String argumentTypeName = toQualifiedName(ref.getTypeName());

		if (resolveFQN(sourceNode, argumentTypeName, BIG_INTEGER) != null) {
			new BigInteger(representation);
			return generateAllocaion(ref, generateStringLiteralArguments(token));
		}
		if (resolveFQN(sourceNode, argumentTypeName, BIG_DECIMAL) != null) {
			new BigInteger(representation);
			return generateAllocaion(ref, generateStringLiteralArguments(token));
		}
		if (resolveFQN(sourceNode, argumentTypeName, DATE) != null) {
			Matcher matcher = DATE_TIME_PATTERN.matcher(representation);
			if (!matcher.matches()) throw new IllegalArgumentException(String.format("cannot parse '%s' as date-time.",
					representation));
			String patternString = "yyyy-MM-dd";
			if (matcher.group(1) != null) {
				patternString = patternString.concat("\'T\'HH:mm:ss");
			}
			if (matcher.group(2) != null) {
				patternString = patternString.concat("'.S");
			}
			StringLiteral pattern = new StringLiteral(patternString.toCharArray(), pS, pE, 0);
			AllocationExpression parsePosition = generateAllocaion(generateTypeReference(ParsePosition.class.getName()),
					IntLiteral.buildIntLiteral(new char[] { '0' }, pS, pE));
			AllocationExpression formatter = generateAllocaion(generateTypeReference(SimpleDateFormat.class.getName()), pattern);

			MessageSend messageSend = new MessageSend();
			messageSend.sourceStart = pS;
			messageSend.sourceEnd = messageSend.statementEnd = pE;
			messageSend.nameSourcePosition = p;
			messageSend.receiver = formatter;
			messageSend.selector = "parse".toCharArray();
			messageSend.arguments = new Expression[] { new StringLiteral(representation.toCharArray(), pS, pE, 0),
					parsePosition };
			return messageSend;
		}

		String fullQualifiedName;
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_DATE, BP_LOCAL_DATE)) != null) {
			LocalDate.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_TIME, BP_LOCAL_TIME)) != null) {
			LocalTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_DATE_TIME, BP_LOCAL_DATE_TIME)) != null) {
			LocalDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_OFFSET_TIME, BP_OFFSET_TIME)) != null) {
			OffsetTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_OFFSET_DATE_TIME, BP_OFFSET_DATE_TIME)) != null) {
			OffsetDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_ZONED_DATE_TIME, BP_ZONED_DATE_TIME)) != null) {
			ZonedDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, token);
		}

		return null;
	}

	private AllocationExpression generateAllocaion(TypeReference ref, Expression... arguments) {
		AllocationExpression allocation = new AllocationExpression();
		allocation.sourceStart = pS;
		allocation.sourceEnd = allocation.statementEnd = pE;
		allocation.type = copyType(ref, source);
		allocation.arguments = arguments;
		return allocation;
	}

	private Expression generateParseMethodCall(String fullyQualifiedName, char[] token) {
		MessageSend messageSend = new MessageSend();
		messageSend.sourceStart = pS;
		messageSend.sourceEnd = messageSend.statementEnd = pE;
		messageSend.nameSourcePosition = p;
		messageSend.receiver = generateNameReference(fullyQualifiedName);
		messageSend.selector = "parse".toCharArray();
		messageSend.arguments = generateStringLiteralArguments(token);
		return messageSend;
	}

	private Expression[] generateStringLiteralArguments(char[] token) {
		return new Expression[] { new StringLiteral(token, pS, pE, 0) };
	}

}
