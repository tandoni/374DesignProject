package problem.interfaces;

import problem.visitor.ITraverser;

public interface IMethod extends ITraverser {
	public int getAccess();
	public String getName();
	public String getDescription();
	public String getSignature();
	public String[] getExceptions();
}
