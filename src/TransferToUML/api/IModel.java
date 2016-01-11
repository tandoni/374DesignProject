package TransferToUML.api;

import java.util.Collection;

import TransferToUML.visitor.ITraverser;

public interface IModel extends ITraverser {
	public void addClass(IClass c);
	public Collection<IClass> getClasses();
	
	public void addRelation(IRelation r);	
//	public void setRelation(IRelation r);	
	public Collection<IRelation> getRelations();
	
	public IClass getNamedClass(String s);
	

}
