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
import TransferToUML.visitor.IVisitor;

public class Model implements IModel {

	public Collection<IClass> classes;
	public Map<String, IRelation> relations;
	// Not technically important to be queue, but this is basically a holding
	// place for potential sequences until ASM is done. Once ASM is done, we
	// have sufficient info to determine if things like constructors are not
	// necessary to create the correct SD diagram.
	public ArrayList<ISequence> sequencesQueue = new ArrayList<ISequence>();
	public ArrayList<ISequence> sequences;
	public ArrayList<String> createdClasses;
	public Collection<String> classNames;
	// This class names is unique for sequence diagrams, because we don't know
	// the class names until after the ASM code has already traversed
	// everything.
	public Collection<String> classNamesSeq = new ArrayList<String>();
	// recordSequence starts false until we find the method that was specified
	// in the "command line", then it is true
	private Boolean recordSequence = false;

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new HashMap<String, IRelation>();
		this.sequences = new ArrayList<ISequence>();
		this.createdClasses = new ArrayList<String>();
		this.classNames = new ArrayList<String>();
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
	public Collection<String> getClassNames() {
		return this.classNames;
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
		v.preVisit(this);
		for (IClass c : this.classes) {
			c.acceptSequence(v);
		}
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public ArrayList<ISequence> getSequences() {
		return this.sequences;
	}

	@Override
	public ArrayList<String> getCreatedClasses() {
		return this.createdClasses;
	}

	public void setRecordSequence(boolean boo) {
		this.recordSequence = boo;
	}

	public boolean getRecordSequence() {
		return this.recordSequence;
	}

	// This method is used to store all potential sequences in a queue. Then,
	// once ASM is done with everything, we'll know exactly what classes must be
	// drawn in the SD. From that info, we can determine all the info we need.
	@Override
	public void addSequenceToQueue(ISequence sequence) {
		this.sequencesQueue.add(sequence);
	}

	public void addSequence(ISequence sequence) {

		if (sequence.equals(null))
			return;
		String fromClass = sequence.getFromClass();
		String toClass = sequence.getToClass();
		String calledMethod = sequence.getCalledMethod();
		if (this.classNamesSeq.contains(fromClass) && this.classNamesSeq.contains(toClass)) {
			if (calledMethod.contains("init>")) {
				sequence.setCalledMethod("new");
				this.createdClasses.add(toClass);
			}
			// System.out.println("adding sequence: from " +
			// sequence.getFromClass() + ", to " + sequence.getToClass()
			// + ", method " + sequence.getCalledMethod());
			this.sequences.add(sequence);
		}

	}

}
