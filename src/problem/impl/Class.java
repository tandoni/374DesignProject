package problem.impl;

import java.util.ArrayList;
import java.util.Collection;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public class Class implements IClass {

	private String name;
	private Collection<IMethod> methods;
	private Collection<IField> fields;
	private Collection<IRelation> relations;
	private String classType;

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
	public String getName() {
		return this.name;
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

	// @Override
	// public void acceptSequence(IVisitor v) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public void acceptSequence(IVisitor v, int depth) {
		// TODO Auto-generated method stub
		System.out.println("CLass : acceptSequence");
	}

}
