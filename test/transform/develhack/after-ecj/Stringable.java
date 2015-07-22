class Stringable {
  @com.develhack.annotation.feature.Stringable class Default {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    private int[] array1;
    private int[][] array2;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Stringable.class) String exclude;
    Default() {
      super();
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") java.lang.String toString() {
      return (((((((((("Stringable.Default(field=" + this.field) + ", finalField=") + this.finalField) + ", transientField=") + this.transientField) + ", array1=") + java.util.Arrays.toString(this.array1)) + ", array2=") + java.util.Arrays.deepToString(this.array2)) + ")");
    }
  }
  @com.develhack.annotation.feature.Stringable(evaluateSuperclass = true) class EvaluateSuperclass extends Thread {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    private int[] array1;
    private int[][] array2;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Stringable.class) String exclude;
    EvaluateSuperclass() {
      super();
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") java.lang.String toString() {
      return (((((((((((("Stringable.EvaluateSuperclass(super=" + super.toString()) + ", field=") + this.field) + ", finalField=") + this.finalField) + ", transientField=") + this.transientField) + ", array1=") + java.util.Arrays.toString(this.array1)) + ", array2=") + java.util.Arrays.deepToString(this.array2)) + ")");
    }
  }
  @com.develhack.annotation.feature.Stringable class Implemented {
    private int field;
    Implemented() {
      super();
    }
    public String toString() {
      return "foo";
    }
  }
  Stringable() {
    super();
  }
}