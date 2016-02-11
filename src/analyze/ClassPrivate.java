package analyze;

public class ClassPrivate extends ProtectedClass implements Interface {
	private ClassWithOneVariable ab;
	private int i = 0;
	private float f = 0f;

	public ClassPrivate() {
		this.ab = new ClassWithOneVariable();
	}

	private float privateFloatOne() {
		return 0f;
	}

}
