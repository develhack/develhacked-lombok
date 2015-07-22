class InitializeFieldsByConstructor {


	@com.develhack.annotation.feature.InitializeFieldsByConstructor
	class Default {
		private int field;
		private final int finalField;
		private final int initializedFinalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class)
		private String exclude;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Default(int finalField) {
			this.finalField = finalField;
		}
	}

	@com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.DEFAULT, allFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC)
	class WithAllFieldsInitializer {
		private int field;
		private final int finalField;
		private final int initializedFinalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class)
		private String exclude;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public WithAllFieldsInitializer(int field, int finalField) {
			this.field = field;
			this.finalField = finalField;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		WithAllFieldsInitializer(int finalField) {
			this.finalField = finalField;
		}
	}

	@com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.NONE, allFieldsInitializer = com.develhack.annotation.feature.Access.PROTECTED)
	class AllFieldsInitializerOnly {
		private int field;
		private final int finalField;
		private final int initializedFinalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.InitializeFieldsByConstructor.class)
		private String exclude;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected AllFieldsInitializerOnly(int field, int finalField) {
			this.field = field;
			this.finalField = finalField;
		}
	}

	@com.develhack.annotation.feature.InitializeFieldsByConstructor(finalFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC, allFieldsInitializer = com.develhack.annotation.feature.Access.PUBLIC)
	class NoTarget {

		private final int initializedFinalField = 0;
	}
}