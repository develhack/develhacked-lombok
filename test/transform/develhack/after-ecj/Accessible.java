class Accessble {
  private int privateField;
  private final @com.develhack.annotation.feature.Accessible int finalField = 0;
  private @com.develhack.annotation.feature.Accessible int accessibleField;
  private transient @com.develhack.annotation.feature.Accessible int transientField;
  private @com.develhack.annotation.feature.Accessible(set = com.develhack.annotation.feature.Access.DEFAULT) int writableFromSamePackageOnly;
  private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED,set = com.develhack.annotation.feature.Access.NONE) int readableFromSubclassOnly;
  private @com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED,set = com.develhack.annotation.feature.Access.PROTECTED) int conflicted;
  private @com.develhack.annotation.feature.Accessible int withJavadoc1;
  private @com.develhack.annotation.feature.Accessible int withJavadoc2;
  Accessble() {
    super();
  }
  public int getConflicted() {
    return this.conflicted;
  }
  public void setConflicted(int conflicted) {
    this.conflicted = conflicted;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getFinalField() {
    return this.finalField;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getAccessibleField() {
    return this.accessibleField;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setAccessibleField(final int accessibleField) {
    this.accessibleField = accessibleField;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getTransientField() {
    return this.transientField;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setTransientField(final int transientField) {
    this.transientField = transientField;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getWritableFromSamePackageOnly() {
    return this.writableFromSamePackageOnly;
  }
  @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setWritableFromSamePackageOnly(final int writableFromSamePackageOnly) {
    this.writableFromSamePackageOnly = writableFromSamePackageOnly;
  }
  protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getReadableFromSubclassOnly() {
    return this.readableFromSubclassOnly;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getWithJavadoc1() {
    return this.withJavadoc1;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setWithJavadoc1(final int withJavadoc1) {
    this.withJavadoc1 = withJavadoc1;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") int getWithJavadoc2() {
    return this.withJavadoc2;
  }
  public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") void setWithJavadoc2(final int withJavadoc2) {
    this.withJavadoc2 = withJavadoc2;
  }
}
