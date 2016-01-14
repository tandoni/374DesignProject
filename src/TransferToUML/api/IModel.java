package TransferToUML.api;

import java.util.ArrayList;
import java.util.Collection;

import TransferToUML.impl.Sequence;
import TransferToUML.visitor.ITraverser;

public interface IModel extends ITraverser {
	public void addClass(IClass c);

	public Collection<IClass> getClasses();

	public void addRelation(IRelation r);

	// public void setRelation(IRelation r);
	public Collection<IRelation> getRelations();

	public IClass getNamedClass(String s);

	public void addSequence(ISequence sequence);

	public ArrayList<ISequence> getSequences();

	ArrayList<String> getCreatedClasses();

	Collection<String> getClassNames();

	public void setRecordSequence(boolean boo);

	public boolean getRecordSequence();

	public void addSequenceToQueue(ISequence sequence);

}
