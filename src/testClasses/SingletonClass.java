package testClasses;

public class SingletonClass {
	
	private static SingletonClass uniqueInstance = new SingletonClass();

	public SingletonClass() {
		// TODO Auto-generated constructor stub
	}
	
	public static SingletonClass getInstance(){
		return uniqueInstance;
	}

}
