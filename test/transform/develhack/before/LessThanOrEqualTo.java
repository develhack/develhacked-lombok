class LessThanOrEqualTo extends Thread {

	static class Inner {
		static double sameName;
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") int arg1) {
		System.out.println(arg1);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") Integer arg1) {
		System.out.println(arg1);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") double arg1) {
		System.out.println(arg1);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") Double arg1) {
		System.out.println(arg1);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("2015-01-01T00:00:00") String arg1) {
		System.out.println(arg1);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") double arg1, @com.develhack.annotation.assertion.LessThanOrEqualTo("2015-01-01T00:00:00") java.util.Date arg2) {
		this(String.valueOf(arg1));
		System.out.println(arg1);
		System.out.println(arg2);
	}

	LessThanOrEqualTo(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") double arg1, @com.develhack.annotation.assertion.LessThanOrEqualTo("2015-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.LessThanOrEqualTo("5") double sameName) {
		super(String.valueOf(Inner.sameName));
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") double arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.LessThanOrEqualTo("arg2") double arg1, double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(double arg1, @com.develhack.annotation.assertion.LessThanOrEqualTo("Double.MAX_VALUE") double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.LessThanOrEqualTo("5") Object arg1) {
		System.out.println(arg1);
	}
}