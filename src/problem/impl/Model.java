package problem.impl;

import org.objectweb.asm.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.app.MyMainApp;
import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public class Model implements IModel {

	public Collection<IClass> classes;
	public Map<String, IRelation> relations;
	public ArrayList<ISequence> sequences;
	public ArrayList<String> createdClasses;
	public Collection<String> classNames;

	private int calldepth = 5;

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
		for (String s : MyMainApp.classes) {
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
	public ArrayList<ISequence> getSequences() {
		return this.sequences;
	}

	@Override
	public ArrayList<String> getCreatedClasses() {
		return this.createdClasses;
	}

	@Override
	public void addSequence(ISequence sequence) {

		if (sequence.equals(null))
			return;
		String fromClass = sequence.getFromClass();
		String toClass = sequence.getToClass();
		String calledMethod = sequence.getCalledMethod();
		if (this.classNames.contains(fromClass) && this.classNames.contains(toClass)) {
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

	// This method searches for the method specified to start the SD production.
	@Override
	public String[] getNewClasses(ISequence subM, int depth) {

		ArrayList<String> classesToAdd = new ArrayList<String>();

		if (depth > 0) {
			for (IClass clazz : this.classes) {
				if (clazz.getName().equals(subM.getToClass())) {
					classesToAdd.add(subM.getFromClass());
					for (IMethod m : clazz.getMethods()) {
						if ((m.getName()).equals(subM.getCalledMethod())) {
							if (this.getArgumentsType(m.getDescription()).equals(subM.getArguments())) {
								classesToAdd.remove(subM.getFromClass());
								for (ISequence innerSM : m.getSubMethods()) {
									classesToAdd.add(innerSM.getFromClass());
									this.getNewClasses(innerSM, depth - 1);
								}

							}
						}
					}
				}
			}
		}

		return classesToAdd.toArray(new String[classesToAdd.size()]);
	}

	@Override
	public void acceptSequence(IVisitor v, ISequence subMethods, int depth) {

		System.out.println("Model : acceptSequence");

		if (depth > 0) {
			// System.out.println("Flag1");
			for (IClass clazz : this.classes) {
				for (IMethod m : clazz.getMethods()) {
					if ((m.getName()).equals(subMethods.getCalledMethod())) {
						// System.out.println("Flag2");
						String[] argTemp = this.getArgumentsType(m.getDescription());
						// System.out.println("argTemp " + argTemp);
						// System.out.println(subMethods.getFromClass() + " " +
						// subMethods.getToClass() + " " +
						// subMethods.getCalledMethod() + " " +
						// subMethods.getArguments());;

						List<String> all = new ArrayList<String>();
						for (String s : argTemp) {
							all.add(s);
						}
						List<String> subs = subMethods.getArguments();
						List<String> subsFinal = new ArrayList<String>();
						for (String s : subs) {
							if (s.contains("<")) {
								String a = s.substring(0, s.indexOf("<"));
								subsFinal.add(a);
								if (s.contains("*"))
									subsFinal.add("Random");
							}
						}

						// if (argTemp.equals(subsMethod.getArgs())) {
						// System.out.println("Flag3");
						for (ISequence innerSubM : m.getSubMethods()) {
							System.out.println(innerSubM.getCalledMethod() + innerSubM.getArguments());
							this.acceptSequence(v, innerSubM, depth - 1);
							// }

						}
					}
				}
			}
		}
		return;
	}

	String[] getArgumentsType(String desc) {
		Type[] args = Type.getArgumentTypes(desc);
		String[] argClassList = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			String[] splitArg1 = arg.split("\\.");
			// String[] splitArg = splitArg1[splitArg1.length - 1].split("<");

			arg = splitArg1[splitArg1.length - 1];
			argClassList[i] = arg;
			// System.out.println("++++++++" + argClassList[i]);
		}
		// System.out.println("++++++++" + argClassList);
		return argClassList;
	}

}