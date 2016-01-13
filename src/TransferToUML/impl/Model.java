package TransferToUML.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import TransferToUML.api.IClass;
import TransferToUML.api.IModel;
import TransferToUML.api.IRelation;
import TransferToUML.api.ISequence;
import TransferToUML.app.TransferToUMLApp;
import TransferToUML.visitor.ITraverser;
import TransferToUML.visitor.IVisitor;

public class Model implements IModel {

	public Collection<IClass> classes;
	public Map<String, IRelation> relations;
	public ArrayList<ISequence> sequences = new ArrayList<ISequence>();
	public ArrayList<String> createdClasses = new ArrayList<String>();
	public Collection<String> classNames = new ArrayList<String>();

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new HashMap<String, IRelation>();
		setClassNames();
	}

	public Model(Collection<IClass> classes) {
		this.classes = classes;
		this.relations = new HashMap<String, IRelation>();
		setClassNames();
	}

	public Model(Collection<IClass> classes, HashMap<String, IRelation> relations) {
		this.classes = classes;
		this.relations = relations;
		setClassNames();
	}

	public void setClassNames() {
		for (String s : TransferToUMLApp.classes) {
			String[] split = s.split("\\.");
			s = split[split.length - 1];
			this.classNames.add(s);
		}
	}

	public void acceptUML(IVisitor v) {
		v.preVisit(this);
		for (IClass c : this.classes) {
			c.acceptUML(v);
		}
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public void addClass(IClass c) {
		this.classes.add(c);
	}

	@Override
	public Collection<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public void addRelation(IRelation r) {

		if (!this.relations.containsKey(r.getSubClass())) {
			this.relations.put(r.getSubClass(), r);

		} else {

			IRelation modify = this.relations.get(r.getSubClass());
			Collection<String> modify_uses = modify.getUses();
			Collection<String> modify_ass = modify.getAssociations();

			if (!r.getUses().isEmpty()) {
				for (String u : r.getUses())
					// if (!modify_uses.contains(u) && !modify_ass.contains(u)
					// && this.classNames.contains(u)) {
					if (!modify_uses.contains(u) && !modify_ass.contains(u)) {
						modify_uses.add(u);
					}
			}

			if (!r.getAssociations().isEmpty()) {
				for (String u : r.getAssociations())
					// if (!modify_ass.contains(u) &&
					// this.classNames.contains(u)) {
					if (!modify_ass.contains(u)) {
						if (modify_uses.contains(u))
							modify_uses.remove(u);
						modify_ass.add(u);
					}
			}
		}
	}

	// @Override
	// public void setRelation(IRelation r) {
	// this.relations.put(r.getSubClass(), r);
	// }

	@Override
	public Collection<IRelation> getRelations() {

		Collection<IRelation> allRelation = new ArrayList<IRelation>();

		for (String key : this.relations.keySet()) {
			allRelation.add(this.relations.get(key));
		}

		return allRelation;
	}

	@Override
	public IClass getNamedClass(String s) {
		for (IClass clazz : this.classes) {
			if (clazz.getName().equals(s)) {
				return clazz;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "classes: " + this.classes + ";" + "Relation: " + this.relations + "; ";
	}

	@Override
	public void acceptSequence(IVisitor v) {
		// TODO Auto-generated method stub

	}

	public void addSequence(ISequence sequence) {

		if (sequence.equals(null))
			return;
		String fromClass = sequence.getFromClass();
		String toClass = sequence.getToClass();
		String calledMethod = sequence.getCalledMethod();
		ArrayList<String> args = sequence.getArguments();

		if (this.classNames.contains(fromClass) && this.classNames.contains(toClass)) {
			if (calledMethod.equals("<init>")) {
				sequence.setCalledMethod("new");
				this.createdClasses.add(toClass);
			}
			System.out.println("adding sequence: from " + sequence.getFromClass() + ", to " + sequence.getToClass()
					+ ", method " + sequence.getCalledMethod());
			this.sequences.add(sequence);
		}

	}

}
