class Negative extends Thread {

	static class Inner {
		static int sameName;
	}

	Negative(@com.develhack.annotation.assertion.Negative int arg1) {
		System.out.println(arg1);
	}

	Negative(@com.develhack.annotation.assertion.Negative Integer arg1) {
		System.out.println(arg1);
	}

	Negative(@com.develhack.annotation.assertion.Negative double arg1) {
		System.out.println(arg1);
	}

	Negative(@com.develhack.annotation.assertion.Negative Double arg1) {
		System.out.println(arg1);
	}

	Negative(@com.develhack.annotation.assertion.Negative String arg1) {
		System.out.println(arg1);
	}

	Negative(@com.develhack.annotation.assertion.Negative int arg1, @com.develhack.annotation.assertion.Negative int arg2) {
		this(String.valueOf(arg1));
		System.out.println(arg1);
		System.out.println(arg2);
	}

	Negative(@com.develhack.annotation.assertion.Negative int arg1, @com.develhack.annotation.assertion.Negative int arg2, @com.develhack.annotation.assertion.Negative int sameName) {
		super(String.valueOf(Inner.sameName));
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.Negative int arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.Negative int arg1, int arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(int arg1, @com.develhack.annotation.assertion.Negative int arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.Negative Object arg1) {
		System.out.println(arg1);
	}
}