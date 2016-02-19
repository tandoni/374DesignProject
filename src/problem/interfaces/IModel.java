package problem.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

public interface IModel extends ITraverser {
	public void addClass(IClass c);

	public Collection<IClass> getClasses();

	public void addRelation(IRelation r);

	public Collection<IRelation> getRelations();

	public Map<String, IRelation> getRelationsMap();

	public IClass getNamedClass(String s);

	public void acceptSequence(IVisitor v, int depth);

	public ArrayList<ISequence> getSequences();

	public void addCreatedClass(String className);

	ArrayList<String> getCreatedClasses();

	/**
	 * Returns the full class name, with slashes
	 * 
	 * @return
	 */
	Collection<String> getFullClassNames();

	/**
	 * Deprecated. Returns shortened class name. Use getFullClassNames()
	 * 
	 * @return
	 */
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

	public void setCallDepth(int depth);

	public void callDepthInc();

	public void addSDClassName(String className);

	public List<String> getSDClassNames();

	public void clearSequences();

	public HashMap<String, Boolean> getContainsPatternMap();

	public void nullifyContainsPatternMap();

	/**
	 * Method that returns the names of the patterns. Will be the keys for the
	 * ContainsPatternMap
	 * 
	 * @return
	 */
	public ArrayList<String> getPatternNames();

	/**
	 * Adds in the string representation for all Patterns, and initializes them
	 * all to false.
	 */
	void initContainsPatternMap();

	/**
	 * This resets the Model back to its original specs
	 */
	public void resetModel();

}
