class DTO {
  @com.develhack.annotation.feature.DTO @java.lang.SuppressWarnings("serial") class Default implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.DTO.class) String exclude;
    Default() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
  }
  @com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT) @java.lang.SuppressWarnings("serial") class ReadableFromSamePackageOnly implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    ReadableFromSamePackageOnly() {
      super();
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
  }
  @com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.DEFAULT) @java.lang.SuppressWarnings("serial") class WritableFromSamePackageOnly implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    WritableFromSamePackageOnly() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
  }
  @com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.NONE) @java.lang.SuppressWarnings("serial") class ReadOnly implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    ReadOnly() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getField() {
      return this.field;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
  }
  @com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE) @java.lang.SuppressWarnings("serial") class WriteOnly implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    WriteOnly() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setField(final int field) {
      this.field = field;
    }
  }
  @com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE,set = com.develhack.annotation.feature.Access.NONE) @java.lang.SuppressWarnings("serial") class Inaccessible implements java.io.Serializable {
    private int field;
    private final int finalField = 0;
    private transient int transientField;
    Inaccessible() {
      super();
    }
  }
  @com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT,set = com.develhack.annotation.feature.Access.DEFAULT) @java.lang.SuppressWarnings({"serial", "javadoc"}) class WithAccessibleAnnotation implements java.io.Serializable {
    private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.NONE,set = com.develhack.annotation.feature.Access.NONE) int privateField;
    private final int finalField = 0;
    private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PUBLIC,set = com.develhack.annotation.feature.Access.PUBLIC) int accessibleField;
    private transient int transientField;
    private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PUBLIC) int writableFromSamePackageOnly;
    private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED,set = com.develhack.annotation.feature.Access.PUBLIC) int readableFromSubclassOnly;
    WithAccessibleAnnotation() {
      super();
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getAccessibleField() {
      return this.accessibleField;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setAccessibleField(final int accessibleField) {
      this.accessibleField = accessibleField;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getWritableFromSamePackageOnly() {
      return this.writableFromSamePackageOnly;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setWritableFromSamePackageOnly(final int writableFromSamePackageOnly) {
      this.writableFromSamePackageOnly = writableFromSamePackageOnly;
    }
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getReadableFromSubclassOnly() {
      return this.readableFromSubclassOnly;
    }
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setReadableFromSubclassOnly(final int readableFromSubclassOnly) {
      this.readableFromSubclassOnly = readableFromSubclassOnly;
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
      return this.finalField;
    }
  }
  DTO() {
    super();
  }
}