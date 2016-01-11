package animal;

public abstract class AbstractCat implements Animal{
	
	protected String family;
	public String typeName;
	public String color;
	
	public AbstractCat() {
		this.family = "cat";
	}
	
	protected void callThisCat() {
		System.out.println("meow~~");
	}

}
