package animal;

public class BombayCat extends AbstractCat{

	private String callingName = "no name";
	
	public BombayCat(String n) {
		this.family = "cat";
		this.typeName = "bombay cat";
		this.color = "black";
		this.setCallingName(n);
	}

	private void setCallingName(String n) {
		this.callingName = n;
	}

	@Override
	public String getCallingName() {
		return this.callingName;
	}
	

	@Override
	public String getDescription() {
		String str = this.callingName + " is a " + this.color + " " + this.typeName + ".";
		return str;
	}

}
