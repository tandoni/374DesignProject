package problem.interfaces;

import java.util.ArrayList;
import java.util.Collection;

import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

public interface IModel extends ITraverser {
	public void addClass(IClass c);

	public Collection<IClass> getClasses();

	public void addRelation(IRelation r);

	public Collection<IRelation> getRelations();

	public IClass getNamedClass(String s);

	public void acceptSequence(IVisitor v, ISequence subMethods, int depth);

	public ArrayList<ISequence> getSequences();

	ArrayList<String> getCreatedClasses();

	Collection<String> getClassNames();

	String[] getNewClasses(ISequence subM, int depth);

	public void addSequence(ISequence sequence);


}
