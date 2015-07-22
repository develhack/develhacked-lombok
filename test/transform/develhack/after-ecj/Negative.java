class Negative extends Thread {
  static class Inner {
    static int sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  Negative(@com.develhack.annotation.assertion.Negative int arg1) {
    super();
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
  }
  Negative(@com.develhack.annotation.assertion.Negative Integer arg1) {
    super();
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
  }
  Negative(@com.develhack.annotation.assertion.Negative double arg1) {
    super();
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
  }
  Negative(@com.develhack.annotation.assertion.Negative Double arg1) {
    super();
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
  }
  Negative(@com.develhack.annotation.assertion.Negative String arg1) {
    super();
    System.out.println(arg1);
  }
  Negative(@com.develhack.annotation.assertion.Negative int arg1, @com.develhack.annotation.assertion.Negative int arg2) {
    this(String.valueOf(com.develhack.Conditions.checkNegative("arg1", arg1)));
    com.develhack.Conditions.checkNegative("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  Negative(@com.develhack.annotation.assertion.Negative int arg1, @com.develhack.annotation.assertion.Negative int arg2, @com.develhack.annotation.assertion.Negative int sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkNegative("sameName", sameName);
    com.develhack.Conditions.checkNegative("arg2", arg2);
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.Negative int arg1) {
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.Negative int arg1, int arg2) {
    com.develhack.Conditions.checkNegative("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(int arg1, @com.develhack.annotation.assertion.Negative int arg2) {
    com.develhack.Conditions.checkNegative("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.Negative Object arg1) {
    System.out.println(arg1);
  }
}
