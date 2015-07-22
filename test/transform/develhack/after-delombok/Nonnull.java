class Nonnull extends Thread {

	static class Inner {

		static String sameName;
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1) {
		com.develhack.Conditions.checkNonnull("arg1", arg1);
		System.out.println(arg1);
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1, @com.develhack.annotation.assertion.Nonnull String arg2) {
		this(com.develhack.Conditions.checkNonnull("arg1", arg1));
		com.develhack.Conditions.checkNonnull("arg2", arg2);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	Nonnull(@com.develhack.annotation.assertion.Nonnull String arg1, @com.develhack.annotation.assertion.Nonnull String arg2, @com.develhack.annotation.assertion.Nonnull String sameName) {
		super(Inner.sameName);
		com.develhack.Conditions.checkNonnull("sameName", sameName);
		com.develhack.Conditions.checkNonnull("arg2", arg2);
		com.develhack.Conditions.checkNonnull("arg1", arg1);
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.Nonnull String arg1) {
		com.develhack.Conditions.checkNonnull("arg1", arg1);
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.Nonnull String arg1, String arg2) {
		com.develhack.Conditions.checkNonnull("arg1", arg1);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(String arg1, @com.develhack.annotation.assertion.Nonnull String arg2) {
		com.develhack.Conditions.checkNonnull("arg2", arg2);
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.Nonnull int arg1) {
		System.out.println(arg1);
	}
}