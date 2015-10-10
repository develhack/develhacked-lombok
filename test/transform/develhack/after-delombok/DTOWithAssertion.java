class DTOWithAssertion {

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithNonnull implements java.io.Serializable {

		@com.develhack.annotation.assertion.Nonnull
		private String[] field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public String[] getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Nonnull String[] field) {
			com.develhack.Conditions.checkNonnull("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithNonempty implements java.io.Serializable {

		@com.develhack.annotation.assertion.Nonempty
		private String[] field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public String[] getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Nonempty String[] field) {
			com.develhack.Conditions.checkNonempty("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithNonzero implements java.io.Serializable {

		@com.develhack.annotation.assertion.Nonzero
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Nonzero int field) {
			com.develhack.Conditions.checkNonzero("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithPositive implements java.io.Serializable {

		@com.develhack.annotation.assertion.Positive
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Positive int field) {
			com.develhack.Conditions.checkPositive("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithNegative implements java.io.Serializable {

		@com.develhack.annotation.assertion.Negative
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Negative int field) {
			com.develhack.Conditions.checkNegative("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithValidNumber implements java.io.Serializable {

		@com.develhack.annotation.assertion.ValidNumber
		private double field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public double getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.ValidNumber double field) {
			com.develhack.Conditions.checkValidNumber("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithFiniteNumber implements java.io.Serializable {

		@com.develhack.annotation.assertion.FiniteNumber
		private double field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public double getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.FiniteNumber double field) {
			com.develhack.Conditions.checkFiniteNumber("field", field);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithGreaterThan implements java.io.Serializable {

		@com.develhack.annotation.assertion.GreaterThan("1")
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.GreaterThan("1") int field) {
			com.develhack.Conditions.checkGreaterThan("field", field, 1);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithGreaterThanOrEqualTo implements java.io.Serializable {

		@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1")
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1") int field) {
			com.develhack.Conditions.checkGreaterThanOrEqualTo("field", field, 1);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithLessThan implements java.io.Serializable {

		@com.develhack.annotation.assertion.LessThan("1")
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.LessThan("1") int field) {
			com.develhack.Conditions.checkLessThan("field", field, 1);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithLessThanOrEqualTo implements java.io.Serializable {

		@com.develhack.annotation.assertion.LessThanOrEqualTo("1")
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.LessThanOrEqualTo("1") int field) {
			com.develhack.Conditions.checkLessThanOrEqualTo("field", field, 1);
			this.field = field;
		}
	}

	@com.develhack.annotation.feature.DTO
	@java.lang.SuppressWarnings("serial")
	class WithInRange implements java.io.Serializable {

		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.InRange(from = "-1", to = "1") int field) {
			com.develhack.Conditions.checkInRange("field", field, -1, 1);
			this.field = field;
		}
	}
}