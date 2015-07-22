class InitializeFieldsByConstructor {
  @com.develhack.annotation.feature.InitializeFieldsByConstructor class Default {
    private int field;
    private final int finalField;
    private final int initializedFinalField = 0;
    private transient int transientField;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class) String exclude;
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") Default(final int finalField) {
      super();
      this.finalField = finalField;
    }
  }
  @com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.DEFAULT,allFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC) class WithAllFieldsInitializer {
    private int field;
    private final int finalField;
    private final int initializedFinalField = 0;
    private transient int transientField;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class) String exclude;
    public @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") WithAllFieldsInitializer(final int field, final int finalField) {
      super();
      this.field = field;
      this.finalField = finalField;
    }
    @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") WithAllFieldsInitializer(final int finalField) {
      super();
      this.finalField = finalField;
    }
  }
  @com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.NONE,allFieldsInitializer = com.develhack.annotation.feature.Access.PROTECTED) class AllFieldsInitializerOnly {
    private int field;
    private final int finalField;
    private final int initializedFinalField = 0;
    private transient int transientField;
    private @com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class) String exclude;
    protected @java.lang.SuppressWarnings("all") @javax.annotation.Generated("lombok") AllFieldsInitializerOnly(final int field, final int finalField) {
      super();
      this.field = field;
      this.finalField = finalField;
    }
  }
  @com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC,allFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC) class NoTarget {
    private final int initializedFinalField = 0;
    NoTarget() {
      super();
    }
  }
  InitializeFieldsByConstructor() {
    super();
  }
}