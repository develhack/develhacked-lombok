class VO {

	@com.develhack.annotation.feature.VO
	class Default {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.VO.class)
		private String exclude;
	}

	@com.develhack.annotation.feature.VO
	class ExtendsObject extends Object {
		private int field;
	}

	@com.develhack.annotation.feature.VO
	class ExtendsNumber extends Thread {
		private int field;
	}

	@com.develhack.annotation.feature.VO
	class HasNonPrivateField {
		int field;
	}

	@com.develhack.annotation.feature.VO()
	class HasSetter {
		private int field;

		void setField(int field) {
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.VO
	class InvalidAccessorVisibility {
		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED, set = com.develhack.annotation.feature.Access.PRIVATE)
		private int field;
	}

	@com.develhack.annotation.feature.VO
	class InvalidSetterVisibility {
		@com.develhack.annotation.feature.Accessible(set = com.develhack.annotation.feature.Access.DEFAULT)
		private int field;
	}
}
