class DTOWithAssertion {
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithNonnull implements java.io.Serializable {
    private @com.develhack.annotation.assertion.Nonnull String[] field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithNonempty implements java.io.Serializable {
    private @com.develhack.annotation.assertion.Nonempty String[] field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithNonzero implements java.io.Serializable {
    private @com.develhack.annotation.assertion.Nonzero int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithPositive implements java.io.Serializable {
    private @com.develhack.annotation.assertion.Positive int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithNegative implements java.io.Serializable {
    private @com.develhack.annotation.assertion.Negative int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithValidNumber implements java.io.Serializable {
    private @com.develhack.annotation.assertion.ValidNumber double field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithFiniteNumber implements java.io.Serializable {
    private @com.develhack.annotation.assertion.FiniteNumber double field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithGreaterThan implements java.io.Serializable {
    private @com.develhack.annotation.assertion.GreaterThan("1") int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithGreaterThanOrEqualTo implements java.io.Serializable {
    private @com.develhack.annotation.assertion.GreaterThanOrEqualTo("1") int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithLessThan implements java.io.Serializable {
    private @com.develhack.annotation.assertion.LessThan("1") int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithLessThanOrEqualTo implements java.io.Serializable {
    private @com.develhack.annotation.assertion.LessThanOrEqualTo("1") int field;
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
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class WithInRange implements java.io.Serializable {
    private @com.develhack.annotation.assertion.InRange(from = "-1",to = "1") int field;
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
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT_1 = 1;
  private static final @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int $INT__1 = (- 1);
  <clinit>() {
  }
  DTOWithAssertion() {
    super();
  }
}