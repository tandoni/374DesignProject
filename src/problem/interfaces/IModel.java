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

	public void addCreatedClass(String className);

	ArrayList<String> getCreatedClasses();

	Collection<String> getClassNames();

	String[] getNewClasses(ISequence subM, int depth);

	public void addSequence(ISequence sequence);

	public void setCurrentClass(String currentClass);

	public String getCurrentClass();

	public void setStartClass(String startClass);

	public String getStartClass();

	public void setStartMethod(String startMethod);

	public String getStartMethodName();

	public String[] getStartMethodArgs();

	public void setRecordSeq(boolean bool);

	public boolean getRecordSeq();

	public void addSingleton(String singleton);

	public ArrayList<String> getSingletons();

	public int getCallDepth();

	public void callDepthInc();

	public void addSDClassName(String className);

	public ArrayList<String> getSDClassNames();

}
