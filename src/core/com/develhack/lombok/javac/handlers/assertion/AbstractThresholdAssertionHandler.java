package com.develhack.lombok.javac.handlers.assertion;

import static com.develhack.lombok.NameResolver.*;
import static lombok.javac.handlers.JavacHandlerUtil.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.core.AnnotationValues.AnnotationValueDecodeFail;
import lombok.javac.Javac;
import lombok.javac.JavacNode;
import lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.OffsetTime;
import org.threeten.bp.ZonedDateTime;

import com.develhack.annotation.assertion.Nullable;
import com.develhack.lombok.NameResolver;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

abstract class AbstractThresholdAssertionHandler<T extends java.lang.annotation.Annotation> extends AbstractAssertionHandler<T> {

	private static final Pattern DATE_TIME_PATTERN = Pattern
			.compile("\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?)?");

	public AbstractThresholdAssertionHandler(Class<T> annotationType) {
		super(annotationType);
	}

	@Override
	public void handle(AnnotationValues<T> annotationValues, JCAnnotation ast, JavacNode annotationNode) {

		// super.handle(annotationValues, ast, annotationNode);
		this.annotationValues = annotationValues;
		this.sourceNode = annotationNode;
		this.source = annotationNode.get();
		this.maker = annotationNode.getTreeMaker();
		for (this.typeNode = sourceNode; this.typeNode != null && this.typeNode.getKind() != Kind.TYPE;) {
			this.typeNode = this.typeNode.up();
		}

		JavacNode variableNode = annotationNode.up();
		if (!(variableNode.get() instanceof JCVariableDecl)) {
			return;
		}

		JCVariableDecl variable = (JCVariableDecl) variableNode.get();

		if (!checkVariableType(variable)) return;

		nullable = findAnnotation(Nullable.class, variable.mods.annotations) != null && !isPrimitiveType(variable);

		switch (variableNode.getKind()) {
			case FIELD:
				for (JavacNode fieldNode : annotationNode.upFromAnnotationToFields()) {
					JCVariableDecl field = (JCVariableDecl) fieldNode.get();
					preProcess(field);
					processConstructor(field);
					processSetter(field);
				}
				return;

			case ARGUMENT:
				JCMethodDecl method = (JCMethodDecl) variableNode.up().get();
				preProcess(variable);
				processArgument(method, variable);
				return;

			default:
				annotationNode.addWarning(String
						.format("@%s is only applicable to the argument or field.", getAnnotationName()));
				return;
		}
	}

	protected abstract Map<String, String> getAdditionalConditionMap();

	protected boolean isFormula(String representation) {

		if (DATE_TIME_PATTERN.matcher(representation).matches()) return false;

		int length = representation.length();
		for (int i = 1; i < length; i++) {
			switch (representation.charAt(i)) {
				case '+':
				case '-':
				case '*':
				case '/':
				case '%':
				case '<':
				case '>':
				case '&':
				case '|':
				case '^':
				case '~':
					return true;
			}
		}
		return false;
	}

	protected boolean isLiteral(String representation) {
		return !Character.isJavaIdentifierStart(representation.codePointAt(0));
	}

	@Override
	protected boolean checkVariableType(JCVariableDecl variable) {

		Map<String, String> additionalCondtionMap;
		try {
			additionalCondtionMap = getAdditionalConditionMap();
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

	protected void preProcess(JCVariableDecl variable) {

		if (typeNode == null) return;
		if (isPrimitiveNumber(variable)) return;

		Map<String, String> additionalCondtionMap;
		try {
			additionalCondtionMap = getAdditionalConditionMap();
		} catch (AnnotationValueDecodeFail e) {
			return;
		}

		JavacNode topTypeNode = resolveTopTypeNode();

		for (Entry<String, String> additionalCondition : additionalCondtionMap.entrySet()) {

			String representation = additionalCondition.getValue();
			if (representation.isEmpty()) continue;

			if (!isLiteral(representation)) continue;

			String fieldName = NameResolver.resolveConstantFieldName(variable.vartype.toString(), representation,
					isBoxedType(variable));
			if (fieldExists(fieldName, topTypeNode) != MemberExistsResult.NOT_EXISTS) continue;

			JCExpression initialization;

			try {

				initialization = isBoxedNumber(variable) ? generatePrimitiveInitializer(representation, variable.vartype)
						: generateReferenceTypeInitializer(representation, variable.vartype);

				if (initialization == null) return;

			} catch (Exception e) {

				annotationValues.setError(additionalCondition.getKey(),
						String.format("cannot parse as %s.", variable.vartype.toString()));

				continue;
			}

			JCVariableDecl field = maker.VarDef(maker.Modifiers(Flags.PRIVATE | Flags.STATIC | Flags.FINAL),
					sourceNode.toName(fieldName), variable.vartype, initialization);

			injectFieldAndMarkGenerated(topTypeNode, recursiveSetGeneratedBy(field));
		}
	}

	@Override
	protected JCMethodInvocation generateCheckMethodCall(JCVariableDecl variable) {

		JCMethodInvocation checkMethodCall = super.generateCheckMethodCall(variable);

		Map<String, String> additionalConditionMap;
		try {
			additionalConditionMap = getAdditionalConditionMap();
		} catch (AnnotationValueDecodeFail e) {
			return null;
		}

		ListBuffer<JCExpression> arguments = new ListBuffer<JCExpression>();
		arguments.addAll(checkMethodCall.args);
		for (Entry<String, String> additionalCondition : additionalConditionMap.entrySet()) {

			String representation = additionalCondition.getValue();

			if (isFormula(representation)) {
				sourceNode.addWarning(String.format(
						"@%s does not support the formula representation. you should implement the check statement.",
						getAnnotationName()));
				return null;
			}

			if (!isLiteral(representation)) {
				arguments.add(generateNameReference(representation));
				continue;
			}

			if (isPrimitiveNumber(variable)) {

				try {

					arguments.add(generatePrimitiveInitializer(representation, variable.vartype));
					continue;

				} catch (Exception e) {
					annotationValues.setError(additionalCondition.getKey(),
							String.format("cannot parse as %s.", variable.vartype.toString()));
					return null;
				}
			}

			String fieldName = NameResolver.resolveConstantFieldName(variable.vartype.toString(), representation,
					isBoxedType(variable));

			if (fieldExists(fieldName, resolveTopTypeNode()) == MemberExistsResult.NOT_EXISTS) {
				sourceNode.addWarning(String.format("@%s does not support the %s. you should implement the check statement.",
						getAnnotationName(), variable.vartype.toString()));
				return null;
			}

			arguments.add(maker.Ident(sourceNode.toName(fieldName)));
		}

		checkMethodCall.args = arguments.toList();
		return checkMethodCall;
	}

	private JCExpression generatePrimitiveInitializer(String representation, JCExpression ref) {

		JCExpression initialization;
		switch (getLastToken(ref).charAt(0)) {
			case 'd':
			case 'D':
				initialization = maker.Literal(Double.parseDouble(representation));
				break;
			case 'f':
			case 'F':
				initialization = maker.Literal(Float.parseFloat(representation));
				break;
			case 'l':
			case 'L':
				initialization = maker.Literal(Long.parseLong(representation));
				break;
			case 'i':
			case 'I':
				initialization = maker.Literal(Integer.parseInt(representation));
				break;
			case 's':
			case 'S':
				initialization = maker.TypeCast(maker.TypeIdent(Javac.CTC_SHORT),
						maker.Literal(Short.parseShort(representation)));
				break;
			case 'b':
			case 'B':
				initialization = maker.TypeCast(maker.TypeIdent(Javac.CTC_BYTE),
						maker.Literal(Short.parseShort(representation)));
				break;
			case 'c':
			case 'C':
				initialization = maker.TypeCast(maker.TypeIdent(Javac.CTC_CHAR),
						maker.Literal(Integer.parseInt(representation)));
				break;
			default:
				return null;
		}

		return initialization;
	}

	private JCExpression generateReferenceTypeInitializer(String representation, JCExpression ref) throws ParseException {

		String argumentTypeName = ref.toString();

		if (resolveFQN(sourceNode, argumentTypeName, BIG_INTEGER) != null) {
			new BigInteger(representation);
			return generateAllocaion(ref, maker.Literal(representation));
		}
		if (resolveFQN(sourceNode, argumentTypeName, BIG_DECIMAL) != null) {
			new BigInteger(representation);
			return generateAllocaion(ref, maker.Literal(representation));
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
			JCLiteral pattern = maker.Literal(patternString);
			JCNewClass formatter = generateAllocaion(generateNameReference(SimpleDateFormat.class.getName()), pattern);
			JCNewClass parsePosition = generateAllocaion(generateNameReference(ParsePosition.class.getName()), maker.Literal(0));
			List<JCExpression> args = List.<JCExpression> of(maker.Literal(representation), parsePosition);
			return maker.Apply(List.<JCExpression> nil(), maker.Select(formatter, sourceNode.toName("parse")), args);
		}

		String fullQualifiedName;
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_DATE, BP_LOCAL_DATE)) != null) {
			LocalDate.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_TIME, BP_LOCAL_TIME)) != null) {
			LocalTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_LOCAL_DATE_TIME, BP_LOCAL_DATE_TIME)) != null) {
			LocalDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_OFFSET_TIME, BP_OFFSET_TIME)) != null) {
			OffsetTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_OFFSET_DATE_TIME, BP_OFFSET_DATE_TIME)) != null) {
			OffsetDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}
		if ((fullQualifiedName = resolveFQN(sourceNode, argumentTypeName, JDK_ZONED_DATE_TIME, BP_ZONED_DATE_TIME)) != null) {
			ZonedDateTime.parse(representation);
			return generateParseMethodCall(fullQualifiedName, representation);
		}

		return null;
	}

	private JCNewClass generateAllocaion(JCExpression ref, JCExpression... arguments) {
		List<JCExpression> typeargs = List.nil();
		List<JCExpression> args = new ListBuffer<JCExpression>().appendArray(arguments).toList();
		return maker.NewClass(null, typeargs, ref, args, null);
	}

	private JCExpression generateParseMethodCall(String fullyQualifiedName, String token) {
		JCExpression meth = generateNameReference(fullyQualifiedName, "parse");
		List<JCExpression> args = generateStringLiteralArguments(token);
		return maker.Apply(List.<JCExpression> nil(), meth, args);
	}

	private List<JCExpression> generateStringLiteralArguments(String token) {
		return List.<JCExpression> of(maker.Literal(token));
	}

}
