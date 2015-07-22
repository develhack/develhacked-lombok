class Positive extends Thread {
  static class Inner {
    static int sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  Positive(@com.develhack.annotation.assertion.Positive int arg1) {
    super();
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
  }
  Positive(@com.develhack.annotation.assertion.Positive Integer arg1) {
    super();
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
  }
  Positive(@com.develhack.annotation.assertion.Positive double arg1) {
    super();
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
  }
  Positive(@com.develhack.annotation.assertion.Positive Double arg1) {
    super();
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
  }
  Positive(@com.develhack.annotation.assertion.Positive String arg1) {
    super();
    System.out.println(arg1);
  }
  Positive(@com.develhack.annotation.assertion.Positive int arg1, @com.develhack.annotation.assertion.Positive int arg2) {
    this(String.valueOf(com.develhack.Conditions.checkPositive("arg1", arg1)));
    com.develhack.Conditions.checkPositive("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  Positive(@com.develhack.annotation.assertion.Positive int arg1, @com.develhack.annotation.assertion.Positive int arg2, @com.develhack.annotation.assertion.Positive int sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkPositive("sameName", sameName);
    com.develhack.Conditions.checkPositive("arg2", arg2);
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.Positive int arg1) {
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.Positive int arg1, int arg2) {
    com.develhack.Conditions.checkPositive("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(int arg1, @com.develhack.annotation.assertion.Positive int arg2) {
    com.develhack.Conditions.checkPositive("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.Positive Object arg1) {
    System.out.println(arg1);
  }
}
