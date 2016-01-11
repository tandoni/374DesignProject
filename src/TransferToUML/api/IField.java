package TransferToUML.api;

import TransferToUML.visitor.ITraverser;

public interface IField extends ITraverser {
	public int getAccess();
	public String getName();
	public String getDescription();
	public String getSignature();
	public Object getValue();
}
