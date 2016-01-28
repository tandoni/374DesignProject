package problem.interfaces;

import java.util.ArrayList;
import java.util.Collection;

import problem.visitor.ITraverser;

public interface IClass extends ITraverser {

	public String getName();

	public Collection<IMethod> getMethods();

	public Collection<IField> getFields();

	public Collection<IRelation> getRelations();

	// public void addName(String s);
	public void addMethod(IMethod m);

	public void addField(IField f);

	public void addRelation(IRelation r);

	public String getClassType();

	// public void setClassType(String s);
	void setClassType(String s);

	void addClassTypes2(String addon);

	ArrayList<String> getClassTypes2();

}
