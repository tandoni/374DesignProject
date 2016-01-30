package problem.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IRelation;
import problem.visitor.IVisitor;

public class Class implements IClass {
	// used to call a SD from this class
	private String fullName = "";
	private String name;
	private Collection<IMethod> methods;
	private Collection<IField> fields;
	private Collection<IRelation> relations;
	private String classType;
	// ClassType2 is for special things used in decorators.
	// The key is the adapter pattern we are storing the element for.
	// The value is the actual class type attribute we want to get.
	private Map<String, String> classTypes2 = new HashMap<String, String>();

	// public Class() {
	// this.methods = new ArrayList<IMethod>();
	// this.fields = new ArrayList<IField>();
	// this.relations = new ArrayList<IRelation>();
	// this.classType = "normal";
	// }

	// Unused
	public Class(String name) {
		this.name = name;
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.relations = new ArrayList<IRelation>();
		this.classType = "normal";
	}

	public Class(String name, String classType) {
		this.name = name;
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.relations = new ArrayList<IRelation>();
		// "Interface", "Abstract"
		this.classType = classType;
	}

	public Class(String fullname, String name, String classType) {
		this.name = name;
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.relations = new ArrayList<IRelation>();
		// "Interface", "Abstract"
		this.classType = classType;
		this.fullName = fullname;
	}

	// This is where the code to write the UML for this class is called
	@Override
	public void acceptUML(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for (IField f : this.fields) {
			f.acceptUML(v);
		}

		if (!this.fields.isEmpty()) {
			v.visitSperator();
		}

		for (IMethod m : this.methods) {
			m.acceptUML(v);
		}
		v.postVisit(this);
	}

	@Override
	public void acceptSpotters(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for (IField f : this.fields) {
			f.acceptSpotters(v);
		}

		if (!this.fields.isEmpty()) {
			v.visitSperator();
		}

		for (IMethod m : this.methods) {
			m.acceptSpotters(v);
		}
		v.postVisit(this);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getFullName() {
		return this.fullName;
	}

	@Override
	public Collection<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public Collection<IField> getFields() {
		return this.fields;
	}

	@Override
	public Collection<IRelation> getRelations() {
		return this.relations;
	}

	// @Override
	// public void addName(String s) {
	// this.name = s;
	// }

	@Override
	public void addMethod(IMethod m) {
		this.methods.add(m);
	}

	@Override
	public void addField(IField f) {
		this.fields.add(f);
	}

	@Override
	public void addRelation(IRelation r) {
		this.relations.add(r);
	}

	@Override
	public String getClassType() {
		return this.classType;
	}

	@Override
	public void setClassType(String s) {
		this.classType = s;
	}

	// ClassType2 is for special things used in decorators.
	@Override
	public void addClassTypes2(String spotter, String addon) {
		this.classTypes2.put(spotter, addon);
	}

	@Override
	public HashMap<String, String> getClassTypes2() {
		return (HashMap<String, String>) this.classTypes2;
	}

	// @Override
	// public void acceptSequence(IVisitor v) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public void acceptSequence(IVisitor v, int depth) {
		// TODO Auto-generated method stub
	}

}
