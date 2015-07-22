class Accessble {

	private int privateField;
	@com.develhack.annotation.feature.Accessible
	private final int finalField = 0;
	@com.develhack.annotation.feature.Accessible
	private int accessibleField;
	@com.develhack.annotation.feature.Accessible
	private transient int transientField;
	@com.develhack.annotation.feature.Accessible(set = com.develhack.annotation.feature.Access.DEFAULT)
	private int writableFromSamePackageOnly;
	@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED, set = com.develhack.annotation.feature.Access.NONE)
	private int readableFromSubclassOnly;
	@com.develhack.annotation.feature.Accessible(get = com.develhack.annotation.feature.Access.PROTECTED, set = com.develhack.annotation.feature.Access.PROTECTED)
	private int conflicted;

	/**
	 * field named withJavadoc1
	 */
	@com.develhack.annotation.feature.Accessible
	private int withJavadoc1;

	/**
	 * field named withJavadoc2
	 */
	@com.develhack.annotation.feature.Accessible
	private int withJavadoc2;

	public int getConflicted() {
		return this.conflicted;
	}

	public void setConflicted(int conflicted) {
		this.conflicted = conflicted;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getFinalField() {
		return this.finalField;
	}

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
	public int getTransientField() {
		return this.transientField;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTransientField(int transientField) {
		this.transientField = transientField;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getWritableFromSamePackageOnly() {
		return this.writableFromSamePackageOnly;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	void setWritableFromSamePackageOnly(int writableFromSamePackageOnly) {
		this.writableFromSamePackageOnly = writableFromSamePackageOnly;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected int getReadableFromSubclassOnly() {
		return this.readableFromSubclassOnly;
	}

	/**
	 * @return field named withJavadoc1
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getWithJavadoc1() {
		return this.withJavadoc1;
	}

	/**
	 * @param withJavadoc1 field named withJavadoc1
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setWithJavadoc1(int withJavadoc1) {
		this.withJavadoc1 = withJavadoc1;
	}

	/**
	 * @return field named withJavadoc2
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getWithJavadoc2() {
		return this.withJavadoc2;
	}

	/**
	 * @param withJavadoc2 field named withJavadoc2
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setWithJavadoc2(int withJavadoc2) {
		this.withJavadoc2 = withJavadoc2;
	}
}