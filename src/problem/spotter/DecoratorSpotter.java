package problem.spotter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;

public class DecoratorSpotter extends PatternSpotter {
	// This is map where the key is the name of the method, and the Collection
	// are the classes that call this method. If multiple classes call the same
	// method, we should be suspicious that this is a decorator.
	static Map<String, Collection<String>> meths = new ConcurrentHashMap<String, Collection<String>>();
	static ArrayList<String> decorates = new ArrayList<String>();
	// variable to check number of decorator method delegations
	int count = 0;
	// Boolean to indicate if this method has been counted already.
	boolean methCounted;

	public DecoratorSpotter(IModel model) {
		super(model);
	}

	public DecoratorSpotter(IModel model, PatternSpotter spotter) {
		super(model, spotter);
	}

	@Override
	// Gathers all methods with more than one class that calls it, and stores
	// those methods here.
	public void visit(IMethod m) {
		super.visit(m);
		// If its a constructor, jsut give up now
		if (m.getName().contains("init>")) {
			return;
		}
		// We need the method to be the name and the parameters for it to work
		// in our sequence generator
		String methName = m.getName() + "(";
		String desc = m.getDescription();
		if (!((!desc.contains(";") && desc.contains(")")) || desc.contains("()"))) {
			// We want this to split because the paramaters for the method
			// should only be the last part (the class)
			desc = desc.split("/")[desc.split("/").length - 1];
			methName = methName + desc.substring(0, desc.indexOf(";")) + " arg0";
		}
		methName = methName + ")";
		// Add this class to the classes which have the method of the same name
		if (!DecoratorSpotter.meths.containsKey(methName)) {
			DecoratorSpotter.meths.put(methName, new ArrayList<String>());
		}
		// list is the list of all classes that call the method of this name
		ArrayList<String> list = (ArrayList<String>) DecoratorSpotter.meths.get(methName);
		list.add(this.curClassFull);
		DecoratorSpotter.meths.put(methName, list);
	}

	@Override
	public void postVisit(IModel m) {
		super.postVisit(m);
		Iterator it = DecoratorSpotter.meths.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			this.methCounted = false;
			ArrayList<String> list = (ArrayList<String>) pair.getValue();
			// THere must be at least 2 classes that call this method
			if (list.size() > 1) {
				String s = "";
				for (String c : list) {
					// Since we know that there's at least one other class that
					// calls this method, it seems like a good time to make some
					// sequences and see if we call the same method on another
					// class. If we
					// do, then we know that this is a decorated thing.
					DesignParser parser = new DesignParser();
					// Since the call depth is static, we must reset it to 0,
					// because if
					// this isn't the first sequence being generated, then it
					// will already be at its previous max
					parser.model.setCallDepth(0);
					// get the size of the sequences, so that we know where to
					// look for ours (since the sequences variable in Model is
					// static).
					parser.model.clearSequences();
					parser.model.setRecordSeq(false);
					String[] str = new String[2];
					// This is the class and method where we want to start the
					// method tracing
					str[0] = c.replace("/", ".") + "." + pair.getKey();
					// Call depth of x
					str[1] = "5";
					try {
						parser.main(str);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Inside of this else, we need to make sure we use
					// parser.model,
					// since we want the model that is stored with the parser
					IModel tempMod = parser.model;
					ArrayList<ISequence> seq = model.getSequences();
					if (seq.isEmpty()) {
					} else {
						String seq0 = seq.get(0).getCalledMethod();
						ArrayList<String> args = seq.get(0).getArguments();
						if (args.size() > 0) {
							if (!args.get(0).equals("")) {
								seq0 = seq0 + "(" + args.get(0) + " arg0)";
							} else {
								seq0 = seq0 + "()";
							}
						} else {
							seq0 = seq0 + "()";
						}
						if (seq0.equals(pair.getKey())) {
							String component = seq.get(0).getToClass();
							String fromClassStr = seq.get(0).getFromClass().replace(".", "/");
							IClass cClass = this.model.getNamedClass(c);
							ArrayList<String> toInter = new ArrayList<String>();
							if (this.model.getRelationsMap().containsKey(component)) {
								toInter = (ArrayList<String>) this.model.getRelationsMap().get(component)
										.getInterfaces();
							}
							// We need to determine if the class that is getting
							// the same method called inside of this class is
							// also a field of this class
							boolean toIsField = false;

							ArrayList<IField> fields = cClass.getFields();
							for (IField field : fields) {
								if (field.getName().equalsIgnoreCase(component)
										&& field.getDescription().contains(component))
									toIsField = true;
							}
							// We need to determine if any of the interfaces of
							// the class that was called inside of this class
							// are a field of this class
							for (String interf : toInter) {
								if (cClass.getFields().contains(interf))
									toIsField = true;
							}
							ArrayList<IMethod> methods = (ArrayList<IMethod>) cClass.getMethods();
							ArrayList<IMethod> constructorMethods = new ArrayList<IMethod>();
							// Iterate through methods, and store any that are a
							// constructor
							for (IMethod met : methods) {
								if (met.getName().contains("init>"))
									constructorMethods.add(met);
							}
							for (IMethod met : constructorMethods) {
								if (met.getDescription().contains(component))
									toIsField = true;
							}

							// Add the decorator labels below
							if (this.model.getFullClassNames().contains(component) && toIsField) {
								if (super.constraint == null) {
									this.model.getNamedClass(component).addClassTypes2(DECORATORSTR, "component");
									this.model.getNamedClass(fromClassStr).addClassTypes2(DECORATORSTR, "decorator");
									String fromClassS = fromClassStr.replace(".", "/");
									IRelation relations = this.model.getRelationsMap().get(fromClassS);
									String superFull = relations.getSuperClass();
									if (superFull != null) {
										IClass next = this.model.getNamedClass(superFull);
										while (!next.getName().equals(component)) {
											next.addClassTypes2(DECORATORSTR, "decorator");
											String supClass = this.model.getRelationsMap().get(next.getFullName())
													.getSuperClass();
											if (supClass == null) {
												break;
											}
											if (supClass.equals("")) {
												ArrayList<String> inter = (ArrayList<String>) this.model
														.getRelationsMap().get(next.getName()).getInterfaces();
												next = this.model.getNamedClass(inter.get(0));
											} else {
												next = this.model.getNamedClass(supClass);
											}
										}
									}
								}
								// In the else, we know there is a constraint,
								// which is the number
								// of methods that are being decorated.
								else {
									if (!this.methCounted) {
										count++;
										this.methCounted = true;
									}
									if (count >= Integer.parseInt(super.constraint)) {
										this.model.getNamedClass(component).addClassTypes2(DECORATORSTR, "component");
										this.model.getNamedClass(fromClassStr).addClassTypes2(DECORATORSTR,
												"decorator");
										String fromClassS = fromClassStr.replace(".", "/");
										IRelation relations = this.model.getRelationsMap().get(fromClassS);
										String superFull = relations.getSuperClass();
										if (superFull != null) {
											IClass next = this.model.getNamedClass(superFull);
											while (!next.getName().equals(component)) {
												next.addClassTypes2(DECORATORSTR, "decorator");
												String supClass = this.model.getRelationsMap().get(next.getFullName())
														.getSuperClass();
												if (supClass == null) {
													break;
												}
												if (supClass.equals("")) {
													ArrayList<String> inter = (ArrayList<String>) this.model
															.getRelationsMap().get(next.getName()).getInterfaces();
													next = this.model.getNamedClass(inter.get(0));
												} else {
													next = this.model.getNamedClass(supClass);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void removeMethsWithOneClass() {
		Set<String> keys = DecoratorSpotter.meths.keySet();
		for (String key : keys) {
			if (DecoratorSpotter.meths.get(key).size() == 1) {
				DecoratorSpotter.meths.remove(key);
			}
		}
	}

}
