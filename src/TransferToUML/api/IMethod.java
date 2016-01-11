package TransferToUML.api;

import TransferToUML.visitor.ITraverser;

public interface IMethod extends ITraverser {
	public int getAccess();
	public String getName();
	public String getDescription();
	public String getSignature();
	public String[] getExceptions();
}
