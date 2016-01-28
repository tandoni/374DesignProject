
public class MethodClassWithFeilds {
	
	public String testPublicStr;
	@SuppressWarnings("unused")
	private int testPrivateInt;
	protected boolean testProtectedBool;

	public MethodClassWithFeilds() {
		
	}
	
	public void doStuff(){
		System.out.println("doStuff");
	}
	
	public void doStuffWithArgs(String s, int n){
		System.out.println("doStuffWithArgs");
	}

}
