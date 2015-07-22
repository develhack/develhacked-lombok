class LessThan extends Thread {
  static class Inner {
    static double sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer $JAVA_LANG_INTEGER_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") double $DOUBLE_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Double $JAVA_LANG_DOUBLE_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") java.util.Date $JAVA_UTIL_DATE_2015_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2015-01-01T00:00:00", new java.text.ParsePosition(0));
  <clinit>() {
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") int arg1) {
    super();
    com.develhack.Conditions.checkLessThan("arg1", arg1, $INT_5);
    System.out.println(arg1);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") Integer arg1) {
    super();
    com.develhack.Conditions.checkLessThan("arg1", arg1, $JAVA_LANG_INTEGER_5);
    System.out.println(arg1);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") double arg1) {
    super();
    com.develhack.Conditions.checkLessThan("arg1", arg1, $DOUBLE_5);
    System.out.println(arg1);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") Double arg1) {
    super();
    com.develhack.Conditions.checkLessThan("arg1", arg1, $JAVA_LANG_DOUBLE_5);
    System.out.println(arg1);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("2015-01-01T00:00:00") String arg1) {
    super();
    System.out.println(arg1);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") double arg1, @com.develhack.annotation.assertion.LessThan("2015-01-01T00:00:00") java.util.Date arg2) {
    this(String.valueOf(com.develhack.Conditions.checkLessThan("arg1", arg1, $DOUBLE_5)));
    com.develhack.Conditions.checkLessThan("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  LessThan(@com.develhack.annotation.assertion.LessThan("5") double arg1, @com.develhack.annotation.assertion.LessThan("2015-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.LessThan("5") double sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkLessThan("sameName", sameName, $DOUBLE_5);
    com.develhack.Conditions.checkLessThan("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00);
    com.develhack.Conditions.checkLessThan("arg1", arg1, $DOUBLE_5);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.LessThan("5") double arg1) {
    com.develhack.Conditions.checkLessThan("arg1", arg1, $DOUBLE_5);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.LessThan("arg2") double arg1, double arg2) {
    com.develhack.Conditions.checkLessThan("arg1", arg1, arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(double arg1, @com.develhack.annotation.assertion.LessThan("Double.MAX_VALUE") double arg2) {
    com.develhack.Conditions.checkLessThan("arg2", arg2, Double.MAX_VALUE);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.LessThan("5") Object arg1) {
    System.out.println(arg1);
  }
}
