class AccessibleWithAssertion {

	class WithNonnull {
		@com.develhack.annotation.assertion.Nonnull
		@com.develhack.annotation.feature.Accessible
		private String[] field;
	}

	class WithNonempty {
		@com.develhack.annotation.assertion.Nonempty
		@com.develhack.annotation.feature.Accessible
		private String[] field;
	}

	class WithNonzero {
		@com.develhack.annotation.assertion.Nonzero
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithPositive {
		@com.develhack.annotation.assertion.Positive
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithNegative {
		@com.develhack.annotation.assertion.Negative
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithValidNumber {
		@com.develhack.annotation.assertion.ValidNumber
		@com.develhack.annotation.feature.Accessible
		private double field;
	}

	class WithFiniteNumber {
		@com.develhack.annotation.assertion.FiniteNumber
		@com.develhack.annotation.feature.Accessible
		private double field;
	}

	class WithGreaterThan {
		@com.develhack.annotation.assertion.GreaterThan("1")
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithGreaterThanOrEqualTo {
		@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1")
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithLessThan {
		@com.develhack.annotation.assertion.LessThan("1")
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithLessThanOrEqualTo {
		@com.develhack.annotation.assertion.LessThanOrEqualTo("1")
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithInRange {
		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		@com.develhack.annotation.feature.Accessible
		private int field;
	}

	class WithNullable {
		@com.develhack.annotation.assertion.Nullable
		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		@com.develhack.annotation.feature.Accessible
		private Integer field;
	}
}