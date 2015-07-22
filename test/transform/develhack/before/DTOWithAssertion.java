class DTOWithAssertion {

	@com.develhack.annotation.feature.DTO
	class WithNonnull {
		@com.develhack.annotation.assertion.Nonnull
		private String[] field;
	}

	@com.develhack.annotation.feature.DTO
	class WithNonempty {
		@com.develhack.annotation.assertion.Nonempty
		private String[] field;
	}

	@com.develhack.annotation.feature.DTO
	class WithNonzero {
		@com.develhack.annotation.assertion.Nonzero
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithPositive {
		@com.develhack.annotation.assertion.Positive
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithNegative {
		@com.develhack.annotation.assertion.Negative
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithValidNumber {
		@com.develhack.annotation.assertion.ValidNumber
		private double field;
	}

	@com.develhack.annotation.feature.DTO
	class WithFiniteNumber {
		@com.develhack.annotation.assertion.FiniteNumber
		private double field;
	}

	@com.develhack.annotation.feature.DTO
	class WithGreaterThan {
		@com.develhack.annotation.assertion.GreaterThan("1")
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithGreaterThanOrEqualTo {
		@com.develhack.annotation.assertion.GreaterThanOrEqualTo("1")
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithLessThan {
		@com.develhack.annotation.assertion.LessThan("1")
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithLessThanOrEqualTo {
		@com.develhack.annotation.assertion.LessThanOrEqualTo("1")
		private int field;
	}

	@com.develhack.annotation.feature.DTO
	class WithInRange {
		@com.develhack.annotation.assertion.InRange(from = "-1", to = "1")
		private int field;
	}
}