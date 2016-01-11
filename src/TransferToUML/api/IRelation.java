package TransferToUML.api;

import java.util.Collection;

import TransferToUML.visitor.ITraverser;

public interface IRelation extends ITraverser{
	
	public String getSubClass();
	public String getSuperClass();
	public Collection<String> getInterfaces();
	public Collection<String> getUses();
	public Collection<String> getAssociations();
	
//	public void addSuperClass(String superClass);
	public void addInterfaces(String[] interfaces);
	public void addUses(String[] usesName);
	public void addUses(String usesName);
	public void addAssociations(String[] associationsName);
	public void addAssociations(String associationsName);
	
}
