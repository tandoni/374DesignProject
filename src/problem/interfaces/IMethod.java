package problem.interfaces;

import java.util.ArrayList;

import problem.visitor.ITraverser;

public interface IMethod extends ITraverser {
	public int getAccess();
	public String getName();
	public String getDescription();
	public String getSignature();
	//public String[] getExceptions();
	public ArrayList<ISequence> getSubMethods();
}
