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
	private int callDepth = 0;
	public ArrayList<ISequence> sequences;
	protected ArrayList<String> singletons = new ArrayList<String>();
	public ArrayList<String> createdClasses = new ArrayList<String>();
	// List of class names that are in the SD diagram!!
	public ArrayList<String> SDClassNames = new ArrayList<String>();
	public Collection<String> classNames;
	// recordSeq is a boolean used when creating SD. It's set to true when we
	// have found the correct class and method that was specified at the start
	// of the program. When this is true, we will begin adding sequences to be
	// generated later
	private boolean recordSeq = false;
	// We don't want to
	// start recording sequences in our SD until after we have found the correct
	// class and method that were specified at the start of the program. These
	// variables represent the method we should start recording sequences.
	// Because of the way asm parses things, it is better to store the method
	// name, and the args as separate entities.
	private String startMethodName = "";
	private String[] startMethodArgs = new String[99];
	// This var is very similar to startMethod (above), but this represents the
	// class we need to start recording the SD.
	private String startClass = "";
	// This is the current class ASM is parsing. This will be updated in
	// DesignParser, and we need to check this against startClass when creating
	// the SD, to know when to start recording sequences.
	private String currentClass = "";

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

	// This is the code where methods are called to generate the UML output.
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
		this.sequences.add(sequence);

		// if (sequence.equals(null))
		// return;
		// String fromClass = sequence.getFromClass();
		// String toClass = sequence.getToClass();
		// String calledMethod = sequence.getCalledMethod();
		// if (this.classNames.contains(fromClass) &&
		// this.classNames.contains(toClass)) {
		// if (calledMethod.contains("init>")) {
		// sequence.setCalledMethod("new");
		// this.createdClasses.add(toClass);
		// }
		// // System.out.println("adding sequence: from " +
		// // sequence.getFromClass() + ", to " + sequence.getToClass()
		// // + ", method " + sequence.getCalledMethod());
		// this.sequences.add(sequence);
		// }

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
		v.preVisit(this);
		for (IClass c : this.classes) {
			c.acceptSequence(v, subMethods, depth);
		}
		v.visit(this);
		v.postVisit(this);

		// if (depth > 0) {
		// // System.out.println("Flag1");
		// for (IClass clazz : this.classes) {
		// for (IMethod m : clazz.getMethods()) {
		// if ((m.getName()).equals(subMethods.getCalledMethod())) {
		// // System.out.println("Flag2");
		// String[] argTemp = this.getArgumentsType(m.getDescription());
		// // System.out.println("argTemp " + argTemp);
		// // System.out.println(subMethods.getFromClass() + " " +
		// // subMethods.getToClass() + " " +
		// // subMethods.getCalledMethod() + " " +
		// // subMethods.getArguments());;
		//
		// List<String> all = new ArrayList<String>();
		// for (String s : argTemp) {
		// all.add(s);
		// }
		// List<String> subs = subMethods.getArguments();
		// List<String> subsFinal = new ArrayList<String>();
		// for (String s : subs) {
		// if (s.contains("<")) {
		// String a = s.substring(0, s.indexOf("<"));
		// subsFinal.add(a);
		// if (s.contains("*"))
		// subsFinal.add("Random");
		// }
		// }
		//
		// // if (argTemp.equals(subsMethod.getArgs())) {
		// // System.out.println("Flag3");
		// for (ISequence innerSubM : m.getSubMethods()) {
		// System.out.println(innerSubM.getCalledMethod() +
		// innerSubM.getArguments());
		// this.acceptSequence(v, innerSubM, depth - 1);
		// // }
		//
		// }
		// }
		// }
		// }
		// }
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

	// Below are getters and setters for currentClass, startClass, startMethod,
	// and recordSeq
	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}

	public String getCurrentClass() {
		return this.currentClass;
	}

	public void setStartClass(String startClass) {
		this.startClass = startClass;
	}

	public String getStartClass() {
		return this.startClass;
	}

	public void setStartMethod(String startMethod) {
		this.startMethodName = startMethod.substring(0, startMethod.indexOf("("));
		String allArgs = startMethod.substring(startMethod.indexOf("(") + 1, startMethod.length() - 1);
		String[] eachArgs = allArgs.split(" ");
		// This will hold only the args, not the params as well
		String[] onlyArgs = new String[(int) Math.ceil(eachArgs.length / 2)];
		// All even indexes in eachArgs are the actual args. Odd lengthed
		// indexes are the names of the params.
		for (int i = 0; i < eachArgs.length; i = i + 2) {
			onlyArgs[onlyArgs.length - 1] = eachArgs[i];
		}
		// Get rid of carrots, they're hard to deal with.
		for (int i = 0; i < onlyArgs.length; i++) {
			int ind = onlyArgs[i].indexOf("<");
			if (ind > -1) {
				onlyArgs[i] = onlyArgs[i].substring(0, ind);
			}
		}
		this.setStartMethodArgs(onlyArgs);

	}

	public void setStartMethodName(String startMethodName) {
		this.startMethodName = startMethodName;
	}

	public void setStartMethodArgs(String[] startArgs) {
		this.startMethodArgs = startArgs;
	}

	public String getStartMethodName() {
		return this.startMethodName;
	}

	public String[] getStartMethodArgs() {
		return this.startMethodArgs;
	}

	public void setRecordSeq(boolean bool) {
		this.recordSeq = bool;
	}

	public boolean getRecordSeq() {
		return this.recordSeq;
	}

	public void addSingleton(String singleton) {
		this.singletons.add(singleton);
	}

	public ArrayList<String> getSingletons() {
		return this.singletons;
	}

	public int getCallDepth() {
		return this.callDepth;
	}

	public void callDepthInc() {
		this.callDepth++;
	}

	@Override
	public void addCreatedClass(String className) {
		this.createdClasses.add(className);
	}

	@Override
	public void addSDClassName(String className) {
		this.SDClassNames.add(className);
	}

	@Override
	public ArrayList<String> getSDClassNames() {
		return this.SDClassNames;
	}

}