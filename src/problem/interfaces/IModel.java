package problem.interfaces;

import java.util.ArrayList;
import java.util.Collection;

import problem.visitor.ITraverser;

public interface IModel extends ITraverser {
	public void addClass(IClass c);

	public Collection<IClass> getClasses();

	public void addRelation(IRelation r);

	public Collection<IRelation> getRelations();

	public IClass getNamedClass(String s);

	public void addSequence(ISequence sequence);

	public ArrayList<ISequence> getSequences();

	ArrayList<String> getCreatedClasses();

	Collection<String> getClassNames();

}
