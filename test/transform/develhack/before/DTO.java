class DTO {

	@com.develhack.annotation.feature.DTO
	class Default {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.DTO.class)
		private String exclude;
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT)
	class ReadableFromSamePackageOnly {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.DEFAULT)
	class WritableFromSamePackageOnly {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(set = com.develhack.annotation.feature.Access.NONE)
	class ReadOnly {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE)
	class WriteOnly {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.NONE, set = com.develhack.annotation.feature.Access.NONE)
	class Inaccessible {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
	}

	@com.develhack.annotation.feature.DTO(get = com.develhack.annotation.feature.Access.DEFAULT, set = com.develhack.annotation.feature.Access.DEFAULT)
	@java.lang.SuppressWarnings({"serial", "javadoc"})
	class WithAccessibleAnnotation {
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
	}
}
