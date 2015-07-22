class Nonnull extends Thread {

	static class Inner {
		static String sameName;
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1) {
		System.out.println(arg1);
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1, @com.develhack.annotation.assertion.Nonnull String arg2) {
		this(arg1);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1, @com.develhack.annotation.assertion.Nonnull String arg2, @com.develhack.annotation.assertion.Nonnull String sameName) {
		super(Inner.sameName);
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.Nonnull String arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.Nonnull String arg1, String arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(String arg1, @com.develhack.annotation.assertion.Nonnull String arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.Nonnull int arg1) {
		System.out.println(arg1);
	}
}