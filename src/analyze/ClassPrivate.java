package analyze;

public class ClassPrivate extends ProtectedClass implements Interface {
	private AbstractClassTwoAbstractMethods ab;
	private int i = 0;
	private float f = 0f;

	public ClassPrivate(AbstractClassTwoAbstractMethods ab) {
		this.ab = ab;
	}

	private float privateFloatOne() {
		return 0f;
	}

}
