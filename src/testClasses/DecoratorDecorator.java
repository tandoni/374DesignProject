package testClasses;

public class DecoratorDecorator extends ComponentDecorator {
	private ComponentInterface compInterface;
	
	public DecoratorDecorator(ComponentInterface i){
		this.compInterface = i;
	}

	@Override
	public void doStuff() {
		super.doStuff();
	}
}
