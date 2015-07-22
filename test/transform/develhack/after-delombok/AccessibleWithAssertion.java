class AccessibleWithAssertion {
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final int $INT_1 = 1;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final int $INT__1 = -1;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Integer $JAVA_LANG_INTEGER__1 = -1;
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	private static final Integer $JAVA_LANG_INTEGER_1 = 1;


	class WithNonnull {

		@com.develhack.annotation.assertion.Nonnull
		@com.develhack.annotation.feature.Accessible
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

	class WithNonempty {

		@com.develhack.annotation.assertion.Nonempty
		@com.develhack.annotation.feature.Accessible
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

	class WithNonzero {

		@com.develhack.annotation.assertion.Nonzero
		@com.develhack.annotation.feature.Accessible
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

	class WithPositive {

		@com.develhack.annotation.assertion.Positive
		@com.develhack.annotation.feature.Accessible
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

	class WithNegative {

		@com.develhack.annotation.assertion.Negative
		@com.develhack.annotation.feature.Accessible
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

	class WithValidNumber {

		@com.develhack.annotation.assertion.ValidNumber
		@com.develhack.annotation.feature.Accessible
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

	class WithFiniteNumber {

		@com.develhack.annotation.assertion.FiniteNumber
		@com.develhack.annotation.feature.Accessible
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

	class WithGreaterThan {

		@com.develhack.annotation.assertion.GreaterThan("1")
		@com.develhack.annotation.feature.Accessible
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.GreaterThan("1") int field) {
			com.develhack.Conditions.checkGreaterThan("field", field, $INT_1);
			this.field = field;
		}
	}

	class WithGreaterThanOrEqualTo {

		@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1")
		@com.develhack.annotation.feature.Accessible
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1") int field) {
			com.develhack.Conditions.checkGreaterThanOrEqualTo("field", field, $INT_1);
			this.field = field;
		}
	}

	class WithLessThan {

		@com.develhack.annotation.assertion.LessThan("1")
		@com.develhack.annotation.feature.Accessible
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.LessThan("1") int field) {
			com.develhack.Conditions.checkLessThan("field", field, $INT_1);
			this.field = field;
		}
	}

	class WithLessThanOrEqualTo {

		@com.develhack.annotation.assertion.LessThanOrEqualTo("1")
		@com.develhack.annotation.feature.Accessible
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.LessThanOrEqualTo("1") int field) {
			com.develhack.Conditions.checkLessThanOrEqualTo("field", field, $INT_1);
			this.field = field;
		}
	}

	class WithInRange {

		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		@com.develhack.annotation.feature.Accessible
		private int field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public int getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.InRange(from = "-1", to = "1") int field) {
			com.develhack.Conditions.checkInRange("field", field, $INT__1, $INT_1);
			this.field = field;
		}
	}

	class WithNullable {

		@com.develhack.annotation.assertion.Nullable
		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		@com.develhack.annotation.feature.Accessible
		private Integer field;

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public Integer getField() {
			return this.field;
		}

		@java.lang.SuppressWarnings("all")
		@javax.annotation.Generated("lombok")
		public void setField(@com.develhack.annotation.assertion.Nullable @com.develhack.annotation.assertion.InRange(from = "-1", to = "1") Integer field) {
			com.develhack.Conditions.checkInRangeIfNonnull("field", field, $JAVA_LANG_INTEGER__1, $JAVA_LANG_INTEGER_1);
			this.field = field;
		}
	}
}