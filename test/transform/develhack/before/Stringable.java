class Stringable {

	@com.develhack.annotation.feature.Stringable
	class Default {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
		private int[] array1;
		private int[][] array2;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Stringable.class)
		private String exclude;
	}

	@com.develhack.annotation.feature.Stringable(evaluateSuperclass = true)
	class EvaluateSuperclass extends Thread {
		private int field;
		private final int finalField = 0;
		private transient int transientField;
		private int[] array1;
		private int[][] array2;
		@com.develhack.annotation.feature.ExcludedFrom(com.develhack.annotation.feature.Stringable.class)
		private String exclude;
	}

	@com.develhack.annotation.feature.Stringable
	class Implemented {
		private int field;
		public String toString() {
			return "foo";
		}
	}
}
