class ValidNumber extends Thread {

	static class Inner {
		static double sameName;
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber int arg1) {
		System.out.println(arg1);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber Integer arg1) {
		System.out.println(arg1);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber double arg1) {
		System.out.println(arg1);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber Double arg1) {
		System.out.println(arg1);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber String arg1) {
		System.out.println(arg1);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber double arg1, @com.develhack.annotation.assertion.ValidNumber double arg2) {
		this(String.valueOf(arg1));
		System.out.println(arg1);
		System.out.println(arg2);
	}

	ValidNumber(@com.develhack.annotation.assertion.ValidNumber double arg1, @com.develhack.annotation.assertion.ValidNumber double arg2, @com.develhack.annotation.assertion.ValidNumber double sameName) {
		super(String.valueOf(Inner.sameName));
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.ValidNumber double arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.ValidNumber double arg1, double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(double arg1, @com.develhack.annotation.assertion.ValidNumber double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.ValidNumber int arg1) {
		System.out.println(arg1);
	}
}