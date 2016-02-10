package problem.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import problem.visitor.ITraverser;

public interface IClass extends ITraverser {

	public String getName();

	public Collection<IMethod> getMethods();

	public ArrayList<IField> getFields();

	public Collection<IRelation> getRelations();

	// public void addName(String s);
	public void addMethod(IMethod m);

	public void addField(IField f);

	public void addRelation(IRelation r);

	public String getClassType();

	// public void setClassType(String s);
	void setClassType(String s);

	void addClassTypes2(String spotter, String addon);

	HashMap<String, String> getClassTypes2();

	String getFullName();

}
