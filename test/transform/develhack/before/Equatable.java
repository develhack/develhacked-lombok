class Equatable {

	@com.develhack.annotation.feature.Equatable
	class Default {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
		private int[] array1;
		private int[][] array2;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Equatable.class)
		private String exclude;
	}

	@com.develhack.annotation.feature.Equatable(evaluateSuperclass = true)
	class EvaluateSuperclass extends Thread {
		private String field;
	}

	@com.develhack.annotation.feature.Equatable(evaluateSuperclass = true)
	class CannotEvaluateSuperclass1 {
		private String field;
	}

	@com.develhack.annotation.feature.Equatable(evaluateSuperclass = true)
	class CannotEvaluateSuperclass2 extends Object {
		private String field;
	}
}
