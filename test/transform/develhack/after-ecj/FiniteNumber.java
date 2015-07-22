class FiniteNumber extends Thread {
  static class Inner {
    static double sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber int arg1) {
    super();
    System.out.println(arg1);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber Integer arg1) {
    super();
    System.out.println(arg1);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber double arg1) {
    super();
    com.develhack.Conditions.checkFiniteNumber("arg1", arg1);
    System.out.println(arg1);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber Double arg1) {
    super();
    com.develhack.Conditions.checkFiniteNumber("arg1", arg1);
    System.out.println(arg1);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber String arg1) {
    super();
    System.out.println(arg1);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber double arg1, @com.develhack.annotation.assertion.FiniteNumber double arg2) {
    this(String.valueOf(com.develhack.Conditions.checkFiniteNumber("arg1", arg1)));
    com.develhack.Conditions.checkFiniteNumber("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  FiniteNumber(@com.develhack.annotation.assertion.FiniteNumber double arg1, @com.develhack.annotation.assertion.FiniteNumber double arg2, @com.develhack.annotation.assertion.FiniteNumber double sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkFiniteNumber("sameName", sameName);
    com.develhack.Conditions.checkFiniteNumber("arg2", arg2);
    com.develhack.Conditions.checkFiniteNumber("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.FiniteNumber double arg1) {
    com.develhack.Conditions.checkFiniteNumber("arg1", arg1);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.FiniteNumber double arg1, double arg2) {
    com.develhack.Conditions.checkFiniteNumber("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(double arg1, @com.develhack.annotation.assertion.FiniteNumber double arg2) {
    com.develhack.Conditions.checkFiniteNumber("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.FiniteNumber int arg1) {
    System.out.println(arg1);
  }
}
