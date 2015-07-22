class Equatable {
  @com.develhack.annotation.feature.Equatable class Default {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    private int[] array1;
    private int[][] array2;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Equatable.class) String exclude;
    Default() {
      super();
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof Equatable.Default)))
          return false;
      final Default other = (Default) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      if ((this.finalField != other.finalField))
          return false;
      if ((! java.util.Arrays.equals(this.array1, other.array1)))
          return false;
      if ((! java.util.Arrays.deepEquals(this.array2, other.array2)))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof Equatable.Default);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      result = ((result * PRIME) + this.finalField);
      result = ((result * PRIME) + java.util.Arrays.hashCode(this.array1));
      result = ((result * PRIME) + java.util.Arrays.deepHashCode(this.array2));
      return result;
    }
  }
  @com.develhack.annotation.feature.Equatable(evaluateSuperclass = true) class EvaluateSuperclass extends Thread {
    private String field;
    EvaluateSuperclass() {
      super();
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof Equatable.EvaluateSuperclass)))
          return false;
      final EvaluateSuperclass other = (EvaluateSuperclass) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((! super.equals(o)))
          return false;
      final java.lang.Object this$field = this.field;
      final java.lang.Object other$field = other.field;
      if (((this$field == null) ? (other$field != null) : (! this$field.equals(other$field))))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof Equatable.EvaluateSuperclass);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + super.hashCode());
      final java.lang.Object $field = this.field;
      result = ((result * PRIME) + (($field == null) ? 0 : $field.hashCode()));
      return result;
    }
  }
  @com.develhack.annotation.feature.Equatable(evaluateSuperclass = true) class CannotEvaluateSuperclass1 {
    private String field;
    CannotEvaluateSuperclass1() {
      super();
    }
  }
  @com.develhack.annotation.feature.Equatable(evaluateSuperclass = true) class CannotEvaluateSuperclass2 extends Object {
    private String field;
    CannotEvaluateSuperclass2() {
      super();
    }
  }
  Equatable() {
    super();
  }
}