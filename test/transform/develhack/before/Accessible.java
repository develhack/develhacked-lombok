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
	/** field named withJavadoc1 */
	@com.develhack.annotation.feature.Accessible
	private int withJavadoc1;
	/**
	 * field named withJavadoc2
	 */
	@com.develhack.annotation.feature.Accessible
	private int withJavadoc2;

	public int getConflicted() { return this.conflicted; }
	public void setConflicted(int conflicted) { this.conflicted = conflicted; }
}