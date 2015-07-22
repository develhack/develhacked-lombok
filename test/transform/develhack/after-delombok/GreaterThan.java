class GreaterThan extends Thread {
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final int $INT_5 = 5;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Integer $JAVA_LANG_INTEGER_5 = 5;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final double $DOUBLE_5 = 5.0;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Double $JAVA_LANG_DOUBLE_5 = 5.0;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final java.util.Date $JAVA_UTIL_DATE_2015_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2015-01-01T00:00:00", new java.text.ParsePosition(0));
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final java.util.Date $JAVA_UTIL_DATE_2015_01_01 = new java.text.SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01", new java.text.ParsePosition(0));

	static class Inner {

		static double sameName;
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") int arg1) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $INT_5);
		System.out.println(arg1);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") Integer arg1) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $JAVA_LANG_INTEGER_5);
		System.out.println(arg1);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") double arg1) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $DOUBLE_5);
		System.out.println(arg1);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") Double arg1) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $JAVA_LANG_DOUBLE_5);
		System.out.println(arg1);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("2015-01-01T00:00:00") String arg1) {
		System.out.println(arg1);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") double arg1, @com.develhack.annotation.assertion.GreaterThan("2015-01-01T00:00:00") java.util.Date arg2) {
		this(String.valueOf(com.develhack.Conditions.checkGreaterThan("arg1", arg1, $DOUBLE_5)));
		com.develhack.Conditions.checkGreaterThan("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	GreaterThan(@com.develhack.annotation.assertion.GreaterThan("5") double arg1, @com.develhack.annotation.assertion.GreaterThan("2015-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.GreaterThan("5") double sameName) {
		super(String.valueOf(Inner.sameName));
		com.develhack.Conditions.checkGreaterThan("sameName", sameName, $DOUBLE_5);
		com.develhack.Conditions.checkGreaterThan("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00);
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $DOUBLE_5);
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.GreaterThan("5") double arg1) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, $DOUBLE_5);
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.GreaterThan("arg2") double arg1, double arg2) {
		com.develhack.Conditions.checkGreaterThan("arg1", arg1, arg2);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(double arg1, @com.develhack.annotation.assertion.GreaterThan("Double.MAX_VALUE") double arg2) {
		com.develhack.Conditions.checkGreaterThan("arg2", arg2, Double.MAX_VALUE);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.GreaterThan("5") Object arg1) {
		System.out.println(arg1);
	}

	public void method5(@com.develhack.annotation.assertion.Nullable @com.develhack.annotation.assertion.GreaterThan("2015-01-01") java.util.Date arg1) {
		com.develhack.Conditions.checkGreaterThanIfNonnull("arg1", arg1, $JAVA_UTIL_DATE_2015_01_01);
		System.out.println(arg1);
	}
}