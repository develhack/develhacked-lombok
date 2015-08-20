class VO {
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class Default implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.VO.class) String exclude;
    Default() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.Default)))
          return false;
      final Default other = (Default) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      if ((this.finalField != other.finalField))
          return false;
      final java.lang.Object this$exclude = this.exclude;
      final java.lang.Object other$exclude = other.exclude;
      if (((this$exclude == null) ? (other$exclude != null) : (! this$exclude.equals(other$exclude))))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.Default);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      result = ((result * PRIME) + this.finalField);
      final java.lang.Object $exclude = this.exclude;
      result = ((result * PRIME) + (($exclude == null) ? 0 : $exclude.hashCode()));
      return result;
    }
  }
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class ExtendsObject extends Object implements java.io.Serializable {
    private int field;
    ExtendsObject() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.ExtendsObject)))
          return false;
      final ExtendsObject other = (ExtendsObject) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.ExtendsObject);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class ExtendsNumber extends Thread implements java.io.Serializable {
    private int field;
    ExtendsNumber() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.ExtendsNumber)))
          return false;
      final ExtendsNumber other = (ExtendsNumber) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((! super.equals(o)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.ExtendsNumber);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + super.hashCode());
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class HasNonPrivateField implements java.io.Serializable {
    int field;
    HasNonPrivateField() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.HasNonPrivateField)))
          return false;
      final HasNonPrivateField other = (HasNonPrivateField) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.HasNonPrivateField);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  @com.develhack.annotation.feature.VO() @java.lang.SuppressWarnings("serial") class HasSetter implements java.io.Serializable {
    private int field;
    HasSetter() {
      super();
    }
    void setField(int field) {
      this.field = field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.HasSetter)))
          return false;
      final HasSetter other = (HasSetter) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.HasSetter);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class InvalidAccessorVisibility implements java.io.Serializable {
    private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED,set = com.develhack.annotation.feature.Access.PRIVATE) int field;
    InvalidAccessorVisibility() {
      super();
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    private @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.InvalidAccessorVisibility)))
          return false;
      final InvalidAccessorVisibility other = (InvalidAccessorVisibility) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.InvalidAccessorVisibility);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  @com.develhack.annotation.feature.VO @java.lang.SuppressWarnings("serial") class InvalidSetterVisibility implements java.io.Serializable {
    private @com.develhack.annotation.feature.Accessible(set = com.develhack.annotation.feature.Access.DEFAULT) int field;
    InvalidSetterVisibility() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean equals(final java.lang.Object o) {
      if ((o == this))
          return true;
      if ((! (o instanceof VO.InvalidSetterVisibility)))
          return false;
      final InvalidSetterVisibility other = (InvalidSetterVisibility) o;
      if ((! other.canEqual((java.lang.Object) this)))
          return false;
      if ((this.field != other.field))
          return false;
      return true;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") boolean canEqual(final java.lang.Object other) {
      return (other instanceof VO.InvalidSetterVisibility);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = ((result * PRIME) + this.field);
      return result;
    }
  }
  VO() {
    super();
  }
}