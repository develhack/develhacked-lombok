class AccessibleWithAssertion {
  class WithNonnull {
    private @com.develhack.annotation.assertion.Nonnull @com.develhack.annotation.feature.Accessible String[] field;
    WithNonnull() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") String[] getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Nonnull String[] field) {
      com.develhack.Conditions.checkNonnull("field", field);
      this.field = field;
    }
  }
  class WithNonempty {
    private @com.develhack.annotation.assertion.Nonempty @com.develhack.annotation.feature.Accessible String[] field;
    WithNonempty() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") String[] getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Nonempty String[] field) {
      com.develhack.Conditions.checkNonempty("field", field);
      this.field = field;
    }
  }
  class WithNonzero {
    private @com.develhack.annotation.assertion.Nonzero @com.develhack.annotation.feature.Accessible int field;
    WithNonzero() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Nonzero int field) {
      com.develhack.Conditions.checkNonzero("field", field);
      this.field = field;
    }
  }
  class WithPositive {
    private @com.develhack.annotation.assertion.Positive @com.develhack.annotation.feature.Accessible int field;
    WithPositive() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Positive int field) {
      com.develhack.Conditions.checkPositive("field", field);
      this.field = field;
    }
  }
  class WithNegative {
    private @com.develhack.annotation.assertion.Negative @com.develhack.annotation.feature.Accessible int field;
    WithNegative() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Negative int field) {
      com.develhack.Conditions.checkNegative("field", field);
      this.field = field;
    }
  }
  class WithValidNumber {
    private @com.develhack.annotation.assertion.ValidNumber @com.develhack.annotation.feature.Accessible double field;
    WithValidNumber() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") double getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.ValidNumber double field) {
      com.develhack.Conditions.checkValidNumber("field", field);
      this.field = field;
    }
  }
  class WithFiniteNumber {
    private @com.develhack.annotation.assertion.FiniteNumber @com.develhack.annotation.feature.Accessible double field;
    WithFiniteNumber() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") double getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.FiniteNumber double field) {
      com.develhack.Conditions.checkFiniteNumber("field", field);
      this.field = field;
    }
  }
  class WithGreaterThan {
    private @com.develhack.annotation.assertion.GreaterThan("1") @com.develhack.annotation.feature.Accessible int field;
    WithGreaterThan() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.GreaterThan("1") int field) {
      com.develhack.Conditions.checkGreaterThan("field", field, $INT_1);
      this.field = field;
    }
  }
  class WithGreaterThanOrEqualTo {
    private @com.develhack.annotation.assertion.GreaterThanOrEqualTo("1") @com.develhack.annotation.feature.Accessible int field;
    WithGreaterThanOrEqualTo() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.GreaterThanOrEqualTo("1") int field) {
      com.develhack.Conditions.checkGreaterThanOrEqualTo("field", field, $INT_1);
      this.field = field;
    }
  }
  class WithLessThan {
    private @com.develhack.annotation.assertion.LessThan("1") @com.develhack.annotation.feature.Accessible int field;
    WithLessThan() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.LessThan("1") int field) {
      com.develhack.Conditions.checkLessThan("field", field, $INT_1);
      this.field = field;
    }
  }
  class WithLessThanOrEqualTo {
    private @com.develhack.annotation.assertion.LessThanOrEqualTo("1") @com.develhack.annotation.feature.Accessible int field;
    WithLessThanOrEqualTo() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.LessThanOrEqualTo("1") int field) {
      com.develhack.Conditions.checkLessThanOrEqualTo("field", field, $INT_1);
      this.field = field;
    }
  }
  class WithInRange {
    private @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") @com.develhack.annotation.feature.Accessible int field;
    WithInRange() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int field) {
      com.develhack.Conditions.checkInRange("field", field, $INT__1, $INT_1);
      this.field = field;
    }
  }
  class WithNullable {
    private @com.develhack.annotation.assertion.Nullable @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") @com.develhack.annotation.feature.Accessible Integer field;
    WithNullable() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final @com.develhack.annotation.assertion.Nullable @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") Integer field) {
      com.develhack.Conditions.checkInRangeIfNonnull("field", field, $JAVA_LANG_INTEGER__1, $JAVA_LANG_INTEGER_1);
      this.field = field;
    }
  }
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_1 = 1;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT__1 = (- 1);
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer $JAVA_LANG_INTEGER__1 = (- 1);
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Integer $JAVA_LANG_INTEGER_1 = 1;
  <clinit>() {
  }
  AccessibleWithAssertion() {
    super();
  }
}