package testClasses;

public class ClassWithUses {

	@SuppressWarnings("unused")
	private EmptyClass clazz = new EmptyClass();
	
	public ClassWithUses(EmptyClass clazz) {
		this.clazz = clazz;
	}

}
