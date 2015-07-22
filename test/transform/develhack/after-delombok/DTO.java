class DTO {


	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class Default implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.DTO.class)
		private String exclude;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(int field) {
			this.field = field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getFinalField() {
			return this.finalField;
		}
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT)
	@java.lang.SuppressWarnings("serial")
	class ReadableFromSamePackageOnly implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(int field) {
			this.field = field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		int getFinalField() {
			return this.finalField;
		}
	}

	@com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.DEFAULT)
	@java.lang.SuppressWarnings("serial")
	class WritableFromSamePackageOnly implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		void setField(int field) {
			this.field = field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getFinalField() {
			return this.finalField;
		}
	}

	@com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.NONE)
	@java.lang.SuppressWarnings("serial")
	class ReadOnly implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getFinalField() {
			return this.finalField;
		}
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE)
	@java.lang.SuppressWarnings("serial")
	class WriteOnly implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(int field) {
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE, set = com.develhack.annotation.feature.Access.NONE)
	@java.lang.SuppressWarnings("serial")
	class Inaccessible implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT, set = com.develhack.annotation.feature.Access.DEFAULT)
	@java.lang.SuppressWarnings({"serial", "javadoc"})
	class WithAccessibleAnnotation implements java.io.Serializable {

		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.NONE, set = com.develhack.annotation.feature.Access.NONE)
		private int privateField;
		private final int finalField = 0;
		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PUBLIC, set = com.develhack.annotation.feature.Access.PUBLIC)
		private int accessibleField;
		private transient int transientField;
		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PUBLIC)
		private int writableFromSamePackageOnly;
		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED, set = com.develhack.annotation.feature.Access.PUBLIC)
		private int readableFromSubclassOnly;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getAccessibleField() {
			return this.accessibleField;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setAccessibleField(int accessibleField) {
			this.accessibleField = accessibleField;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getWritableFromSamePackageOnly() {
			return this.writableFromSamePackageOnly;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setWritableFromSamePackageOnly(int writableFromSamePackageOnly) {
			this.writableFromSamePackageOnly = writableFromSamePackageOnly;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected int getReadableFromSubclassOnly() {
			return this.readableFromSubclassOnly;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setReadableFromSubclassOnly(int readableFromSubclassOnly) {
			this.readableFromSubclassOnly = readableFromSubclassOnly;
		}


		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		int getFinalField() {
			return this.finalField;
		}
	}
}
