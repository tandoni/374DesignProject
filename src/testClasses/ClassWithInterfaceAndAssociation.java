package testClasses;

public class ClassWithInterfaceAndAssociation implements EmptyInterface{
	
	@SuppressWarnings("unused")
	private EmptyClass clazz;
	
	public ClassWithInterfaceAndAssociation() {
		this.clazz = new EmptyClass();
	}

}
