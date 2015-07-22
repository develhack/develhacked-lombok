class Nonempty extends Thread {

	static class Inner {
		static String sameName;
	}

	Nonempty(@com.develhack.annotation.assertion.Nonempty String arg1) {
		System.out.println(arg1);
	}

	Nonempty(@com.develhack.annotation.assertion.Nonempty String arg1, @com.develhack.annotation.assertion.Nonempty String arg2) {
		this(arg1);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	Nonempty(@com.develhack.annotation.assertion.Nonempty String arg1, @com.develhack.annotation.assertion.Nonempty String arg2, @com.develhack.annotation.assertion.Nonempty String sameName) {
		super(Inner.sameName);
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.Nonempty String arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.Nonempty String arg1, String arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(String arg1, @com.develhack.annotation.assertion.Nonempty String arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.Nonempty int arg1) {
		System.out.println(arg1);
	}
}