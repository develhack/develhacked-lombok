class InRange extends Thread {
  static class Inner {
    static double sameName;
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT__1 = (- 1);
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_1 = 1;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_10 = 10;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer $JAVA_LANG_INTEGER_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer $JAVA_LANG_INTEGER_10 = 10;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") double $DOUBLE_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") double $DOUBLE_10 = 10;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Double $JAVA_LANG_DOUBLE_5 = 5;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Double $JAVA_LANG_DOUBLE_10 = 10;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") java.util.Date $JAVA_UTIL_DATE_2015_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2015-01-01T00:00:00", new java.text.ParsePosition(0));
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") java.util.Date $JAVA_UTIL_DATE_2016_01_01_00_00_00 = new java.text.SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss").parse("2016-01-01T00:00:00", new java.text.ParsePosition(0));
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_2 = 2;
  private @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int hasSetter;
  private @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int hasNoAnnotationOnSetter;
  private @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int hasDifferentValuesOnSetter;
  <clinit>() {
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") int arg1) {
    super();
    com.develhack.Conditions.checkInRange("arg1", arg1, $INT_5, $INT_10);
    System.out.println(arg1);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") Integer arg1) {
    super();
    com.develhack.Conditions.checkInRange("arg1", arg1, $JAVA_LANG_INTEGER_5, $JAVA_LANG_INTEGER_10);
    System.out.println(arg1);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") double arg1) {
    super();
    com.develhack.Conditions.checkInRange("arg1", arg1, $DOUBLE_5, $DOUBLE_10);
    System.out.println(arg1);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") Double arg1) {
    super();
    com.develhack.Conditions.checkInRange("arg1", arg1, $JAVA_LANG_DOUBLE_5, $JAVA_LANG_DOUBLE_10);
    System.out.println(arg1);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00",to = "2016-01-01T00:00:00") String arg1) {
    super();
    System.out.println(arg1);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") double arg1, @com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00",to = "2016-01-01T00:00:00") java.util.Date arg2) {
    this(String.valueOf(com.develhack.Conditions.checkInRange("arg1", arg1, $DOUBLE_5, $DOUBLE_10)));
    com.develhack.Conditions.checkInRange("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00, $JAVA_UTIL_DATE_2016_01_01_00_00_00);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  InRange(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") double arg1, @com.develhack.annotation.assertion.InRange(from = "2015-01-01T00:00:00",to = "2016-01-01T00:00:00") java.util.Date arg2, @com.develhack.annotation.assertion.InRange(from = "5",to = "10") double sameName) {
    super(String.valueOf(Inner.sameName));
    com.develhack.Conditions.checkInRange("sameName", sameName, $DOUBLE_5, $DOUBLE_10);
    com.develhack.Conditions.checkInRange("arg2", arg2, $JAVA_UTIL_DATE_2015_01_01_00_00_00, $JAVA_UTIL_DATE_2016_01_01_00_00_00);
    com.develhack.Conditions.checkInRange("arg1", arg1, $DOUBLE_5, $DOUBLE_10);
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(sameName);
  }
  public void method1(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") double arg1) {
    com.develhack.Conditions.checkInRange("arg1", arg1, $DOUBLE_5, $DOUBLE_10);
    System.out.println(arg1);
  }
  public void method2(@com.develhack.annotation.assertion.InRange(from = "5",to = "arg2") double arg1, double arg2) {
    com.develhack.Conditions.checkInRange("arg1", arg1, $DOUBLE_5, arg2);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method3(double arg1, @com.develhack.annotation.assertion.InRange(from = "Double.MIN_VALUE",to = "Double.MAX_VALUE") double arg2) {
    com.develhack.Conditions.checkInRange("arg2", arg2, Double.MIN_VALUE, Double.MAX_VALUE);
    System.out.println(arg1);
    System.out.println(arg2);
  }
  public void method4(@com.develhack.annotation.assertion.InRange(from = "5",to = "10") Object arg1) {
    System.out.println(arg1);
  }
  public void setHasSetter(@com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int hasSetter) {
    com.develhack.Conditions.checkInRange("hasSetter", hasSetter, $INT__1, $INT_1);
  }
  public void setHasNoAnnotationOnSetter(int hasSetter) {
  }
  public void setHasDifferentValuesOnSetter(@com.develhack.annotation.assertion.InRange(from = "-1",to = "2") int hasSetter) {
    com.develhack.Conditions.checkInRange("hasSetter", hasSetter, $INT__1, $INT_2);
  }
}