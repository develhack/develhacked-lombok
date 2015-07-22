class InRange extends Thread {

	static class Inner {
		static double sameName;
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") int arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") Integer arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") double arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") Double arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="2015-01-01T00:00:00", to="2016-01-01T00:00:00") String arg1) {
		System.out.println(arg1);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") double arg1, @com.develhack.annotation.assertion.InRange(from="2015-01-01T00:00:00", to="2016-01-01T00:00:00") java.util.Date arg2) {
		this(String.valueOf(arg1));
		System.out.println(arg1);
		System.out.println(arg2);
	}

	InRange(@com.develhack.annotation.assertion.InRange(from="5", to="10") double arg1, @com.develhack.annotation.assertion.InRange(from="2015-01-01T00:00:00", to="2016-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.InRange(from="5", to="10") double sameName) {
		super(String.valueOf(Inner.sameName));
		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(sameName);
	}

	public void method1(@com.develhack.annotation.assertion.InRange(from="5", to="10") double arg1) {
		System.out.println(arg1);
	}

	public void method2(@com.develhack.annotation.assertion.InRange(from="5", to="arg2") double arg1, double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method3(double arg1, @com.develhack.annotation.assertion.InRange(from="Double.MIN_VALUE", to="Double.MAX_VALUE") double arg2) {
		System.out.println(arg1);
		System.out.println(arg2);
	}

	public void method4(@com.develhack.annotation.assertion.InRange(from="5", to="10") Object arg1) {
		System.out.println(arg1);
	}

	@com.develhack.annotation.assertion.InRange(from="-1", to="1")
	private int hasSetter;
	@com.develhack.annotation.assertion.InRange(from="-1", to="1")
	private int hasNoAnnotationOnSetter;
	@com.develhack.annotation.assertion.InRange(from="-1", to="1")
	private int hasDifferentValuesOnSetter;

	public void setHasSetter(@com.develhack.annotation.assertion.InRange(from="-1", to="1")int hasSetter) {
	}

	public void setHasNoAnnotationOnSetter(int hasSetter) {
	}

	public void setHasDifferentValuesOnSetter(@com.develhack.annotation.assertion.InRange(from="-1", to="2")int hasSetter) {
	}
}