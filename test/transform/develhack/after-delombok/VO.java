class VO {


	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class Default implements java.io.Serializable {

		private int field;
		private final int finalField = 0;
		private transient int transientField;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.VO.class)
		private String exclude;

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

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.Default)) return false;
			final Default other = (Default)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			if (this.finalField != other.finalField) return false;
			final java.lang.Object this$exclude = this.exclude;
			final java.lang.Object other$exclude = other.exclude;
			if (this$exclude == null ? other$exclude != null : !this$exclude.equals(other$exclude)) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.Default;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			result = result * PRIME + this.finalField;
			final java.lang.Object $exclude = this.exclude;
			result = result * PRIME + ($exclude == null ? 43 : $exclude.hashCode());
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class ExtendsObject extends Object implements java.io.Serializable {

		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.ExtendsObject)) return false;
			final ExtendsObject other = (ExtendsObject)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.ExtendsObject;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class ExtendsNumber extends Thread implements java.io.Serializable {

		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.ExtendsNumber)) return false;
			final ExtendsNumber other = (ExtendsNumber)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (!super.equals(o)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.ExtendsNumber;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + super.hashCode();
			result = result * PRIME + this.field;
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class HasNonPrivateField implements java.io.Serializable {

		int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.HasNonPrivateField)) return false;
			final HasNonPrivateField other = (HasNonPrivateField)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.HasNonPrivateField;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class HasSetter implements java.io.Serializable {

		private int field;

		void setField(int field) {
			this.field = field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.HasSetter)) return false;
			final HasSetter other = (HasSetter)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.HasSetter;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class InvalidAccessorVisibility implements java.io.Serializable {

		@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED, set = com.develhack.annotation.feature.Access.PRIVATE)
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		private void setField(int field) {
			this.field = field;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.InvalidAccessorVisibility)) return false;
			final InvalidAccessorVisibility other = (InvalidAccessorVisibility)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.InvalidAccessorVisibility;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			return result;
		}
	}

	@com.develhack.annotation.feature.VO
	@java.lang.SuppressWarnings("serial")
	class InvalidSetterVisibility implements java.io.Serializable {

		@com.develhack.annotation.feature.Accessible(set = com.develhack.annotation.feature.Access.DEFAULT)
		private int field;

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

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public boolean equals(final java.lang.Object o) {
			if (o == this) return true;
			if (!(o instanceof VO.InvalidSetterVisibility)) return false;
			final InvalidSetterVisibility other = (InvalidSetterVisibility)o;
			if (!other.canEqual((java.lang.Object)this)) return false;
			if (this.field != other.field) return false;
			return true;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		protected boolean canEqual(final java.lang.Object other) {
			return other instanceof VO.InvalidSetterVisibility;
		}

		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			result = result * PRIME + this.field;
			return result;
		}
	}
}