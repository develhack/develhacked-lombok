class Nonzero extends Thread {
  static class Inner {
    static int sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero int arg1) {
    super();
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero Integer arg1) {
    super();
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero double arg1) {
    super();
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero Double arg1) {
    super();
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero String arg1) {
    super();
    System.out.println(arg1);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero int arg1, @com.develhack.annotation.assertion.Nonzero int arg2) {
    this(String.valueOf(com.develhack.Conditions.checkNonzero("arg1", arg1)));
    com.develhack.Conditions.checkNonzero("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  Nonzero(@com.develhack.annotation.assertion.Nonzero int arg1, @com.develhack.annotation.assertion.Nonzero int arg2, @com.develhack.annotation.assertion.Nonzero int sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkNonzero("sameName", sameName);
    com.develhack.Conditions.checkNonzero("arg2", arg2);
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.Nonzero int arg1) {
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.Nonzero int arg1, int arg2) {
    com.develhack.Conditions.checkNonzero("arg1", arg1);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(int arg1, @com.develhack.annotation.assertion.Nonzero int arg2) {
    com.develhack.Conditions.checkNonzero("arg2", arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.Nonzero Object arg1) {
    System.out.println(arg1);
  }
  public void method5(@com.develhack.annotation.assertion.Nonzero @com.develhack.annotation.assertion.Nullable Integer arg1) {
    com.develhack.Conditions.checkNonzeroIfNonnull("arg1", arg1);
    System.out.println(arg1);
  }
}
