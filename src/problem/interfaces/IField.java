package problem.interfaces;

import problem.visitor.ITraverser;

public interface IField extends ITraverser {
	public int getAccess();
	public String getName();
	public String getDescription();
	public String getSignature();
	public Object getValue();
}
