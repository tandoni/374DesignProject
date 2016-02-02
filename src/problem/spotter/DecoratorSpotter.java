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
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;

public class DecoratorSpotter extends PatternSpotterDec {
	// This is map where the key is the name of the method, and the Collection
	// are the classes that call this method. If multiple classes call the same
	// method, we should be suspicious that this is a decorator.
	static Map<String, Collection<String>> meths = new ConcurrentHashMap<String, Collection<String>>();
	static ArrayList<String> decorates = new ArrayList<String>();

	public DecoratorSpotter(IModel model, PatternSpotter spotter) {
		super(model, spotter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(IMethod m) {
		super.visit(m);
		// If its a constructor, jsut give up now
		if (m.getName().contains("init>")) {
			return;
		}
		// Add this class to the classes which have the method of the same name
		if (!DecoratorSpotter.meths.containsKey(m.getName())) {
			DecoratorSpotter.meths.put(m.getName(), new ArrayList<String>());
		}
		// list is the list of all classes that call the method of this name
		ArrayList<String> list = (ArrayList<String>) DecoratorSpotter.meths.get(m.getName());
		list.add(this.curClassFull);
		DecoratorSpotter.meths.put(m.getName(), list);
	}

	// if (!DecoratorSpotter.decorates.contains(this.curClass)
	// &&
	// !this.model.getNamedClass(curClass).getClassTypes2().containsKey(ADAPTERSTR))
	// {
	// DecoratorSpotter.decorates.add(this.curClass);
	// this.model.getNamedClass(this.curClass).addClassTypes2(PatternSpotter.DECORATORSTR,
	// "decorator");
	// }

	@Override
	public void postVisit(IModel m) {
		Iterator it = DecoratorSpotter.meths.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ArrayList<String> list = (ArrayList<String>) pair.getValue();
			// THere must be at least 2 classes that call this method
			if (list.size() > 1) {
				for (String c : list) {
					// Since we know that there's at least one other class that
					// calls
					// this method, it seems like a good time to make some
					// sequences
					// and
					// see if we call the same method on another class. If we
					// do,
					// then
					// we know that this is a decorated thing.
					DesignParser parser = new DesignParser();
					// Since the call depth is static, we must reset it to 0,
					// because if
					// this isn't the first sequence being generated, then it
					// will already be at its previous max
					parser.model.setCallDepth(0);
					// get the size of the sequences, so that we know where to
					// look
					// for ours (since the sequences variable in Model is
					// static).
					parser.model.clearSequences();
					parser.model.setRecordSeq(false);
					String[] str = new String[2];
					// This is the class and method where we want to start the
					// method
					// tracing
					str[0] = c.replace("/", ".") + "." + pair.getKey() + "()";
					// str[0] = "problem.z.decorator.Mocha.cost()";
					// Call depth of x
					str[1] = "5";
					try {
						parser.main(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Inside of this else, we need to make sure we use
					// parser.model,
					// since we want the model that is stored with the parser

					IModel tempMod = parser.model;
					ArrayList<ISequence> seq = model.getSequences();
					if (seq.isEmpty()) {
					} else {
						if (seq.get(0).getCalledMethod().equals(pair.getKey())) {
							String component = seq.get(0).getToClass();
							String fromClass = seq.get(0).getFromClass();
							String[] fromSplit = fromClass.split("\\.");
							String fromClassShortName = fromSplit[fromSplit.length - 1];
							this.model.getNamedClass(component).addClassTypes2(DECORATORSTR, "component");
							this.model.getNamedClass(fromClassShortName).addClassTypes2(DECORATORSTR, "decorator");
							String fromClassS = fromClass.replace(".", "/");
							// IClass clas =
							// this.model.getNamedClass(fromClassShortName);
							// Collection<IRelation> rels = clas.getRelations();
							IRelation relations = this.model.getRelationsMap().get(fromClassS);
							String superFull = relations.getSuperClass();
							if (superFull != null) {
								superFull = superFull.split("/")[superFull.split("/").length - 1];
								IClass next = this.model.getNamedClass(superFull);
								while (!next.getName().equals(component)) {
									next.addClassTypes2(DECORATORSTR, "decorator");
									String supClass = this.model.getRelationsMap().get(next.getFullName())
											.getSuperClass();
									if (supClass == null) {
										break;
									}
									if (supClass.equals("")) {
										ArrayList<String> inter = (ArrayList<String>) this.model.getRelationsMap()
												.get(next.getName()).getInterfaces();
										next = this.model.getNamedClass(inter.get(0));
									} else {
										supClass = supClass.split("/")[supClass.split("/").length - 1];
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

	private void removeMethsWithOneClass() {
		Set<String> keys = DecoratorSpotter.meths.keySet();
		for (String key : keys) {
			if (DecoratorSpotter.meths.get(key).size() == 1) {
				DecoratorSpotter.meths.remove(key);
			}
		}
	}

}
