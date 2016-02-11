package problem.interfaces;

import java.util.ArrayList;

import problem.visitor.ITraverser;

public interface ISequence extends ITraverser{
	public void addFromClass(String fromClass);

	public void addToClass(String toClass);

	public void addCalledMethod(String calledMethod);

	public void addArguments(String[] arguments);

	public String getFromClass();

	public String getToClass();

	public String getCalledMethod();

	public ArrayList<String> getArguments();

	public void setCalledMethod(String string);
}
