class InRange extends Thread {
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Integer $JAVA_LANG_INTEGER_5 = 5;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Integer $JAVA_LANG_INTEGER_10 = 10;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Double $JAVA_LANG_DOUBLE_5 = 5.0;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Double $JAVA_LANG_DOUBLE_10 = 10.0;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final java.util.Date $JAVA_UTIL_DATE_2015_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2015-01-01T00:00:00", new java.text.ParsePosition(0));
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final java.util.Date $JAVA_UTIL_DATE_2016_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2016-01-01T00:00:00", new java.text.ParsePosition(0));

	static class Inner {

		static double sameName;
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") int arg1) {
		com.develhack.Conditions.checkInRange("arg1", arg1, 5, 10);
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") Integer arg1) {
		com.develhack.Conditions.checkInRange("arg1", arg1, $JAVA_LANG_INTEGER_5, $JAVA_LANG_INTEGER_10);
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") double arg1) {
		com.develhack.Conditions.checkInRange("arg1", arg1, 5.0, 10.0);
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") Double arg1) {
		com.develhack.Conditions.checkInRange("arg1", arg1, $JAVA_LANG_DOUBLE_5, $JAVA_LANG_DOUBLE_10);
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00", to = "2016-01-01T00:00:00") String arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") double arg1, @com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00", to = "2016-01-01T00:00:00") java.util.Date arg2) {
		this(String.valueOf(com.develhack.Conditions.checkInRange("arg1", arg1, 5.0, 10.0)));
		com.develhack.Conditions.checkInRange("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00, $JAVA_UTIL_DATE_2016_01_01_00_00_00);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") double arg1, @com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00", to = "2016-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.InRange(from = "5", to = "10") double sameName) {
		super(String.valueOf(Inner.sameName));
		com.develhack.Conditions.checkInRange("sameName", sameName, 5.0, 10.0);
		com.develhack.Conditions.checkInRange("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00, $JAVA_UTIL_DATE_2016_01_01_00_00_00);
		com.develhack.Conditions.checkInRange("arg1", arg1, 5.0, 10.0);
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") double arg1) {
		com.develhack.Conditions.checkInRange("arg1", arg1, 5.0, 10.0);
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.InRange(from = "5", to = "arg2") double arg1, double arg2) {
		com.develhack.Conditions.checkInRange("arg1", arg1, 5.0, arg2);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(double arg1, @com.develhack.annotation.assertion.InRange(from = "Double.MIN_VALUE", to = "Double.MAX_VALUE") double arg2) {
		com.develhack.Conditions.checkInRange("arg2", arg2, Double.MIN_VALUE, Double.MAX_VALUE);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.InRange(from = "5", to = "10") Object arg1) {
		System.out.println(arg1);
	}
	@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
	private int hasSetter;
	@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
	private int hasNoAnnotationOnSetter;
	@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
	private int hasDifferentValuesOnSetter;

	public void setHasSetter(@com.develhack.annotation.assertion.InRange(from = "-1", to = "1") int hasSetter) {
		com.develhack.Conditions.checkInRange("hasSetter", hasSetter, -1, 1);
	}

	public void setHasNoAnnotationOnSetter(int hasSetter) {
	}

	public void setHasDifferentValuesOnSetter(@com.develhack.annotation.assertion.InRange(from = "-1", to = "2") int hasSetter) {
		com.develhack.Conditions.checkInRange("hasSetter", hasSetter, -1, 2);
	}
}