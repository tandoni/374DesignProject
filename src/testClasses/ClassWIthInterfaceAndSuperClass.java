package testClasses;

public class ClassWIthInterfaceAndSuperClass extends EmptyClass implements EmptyInterface{
	
	private String str;
	
	public ClassWIthInterfaceAndSuperClass(){
	}
	
	public void setStr(String s){
		this.str = s;
	}
	
	public String gteStr(){
		return this.str;
	}

}

