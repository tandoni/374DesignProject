package problem.spotter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	private static Collection<String> decorates = new ArrayList<String>();

	public DecoratorSpotter(IModel model, PatternSpotter spotter) {
		super(model, spotter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(IMethod m) {
		super.visit(m);
		// If its a constructor, jsut give up now
		if (m.getName().equals("<init>"))
			return;
		// Add this class to the classes which have the method of the same name
		if (!DecoratorSpotter.meths.containsKey(m.getName())) {
			DecoratorSpotter.meths.put(m.getName(), new ArrayList<String>());
		}
		ArrayList<String> list = (ArrayList<String>) DecoratorSpotter.meths.get(m.getName());
		int size = list.size();
		list.add(this.curClassFull);
		// If list is empty, then we know that this is isn't able to be
		// decorated, so we simply add the current class as the first class
		// which calls this method.
		if (size == 0) {
			DecoratorSpotter.meths.put(m.getName(), list);
		} else {
			// add this class to the list of classes that call this method
			DecoratorSpotter.meths.put(m.getName(), list);
			// Since we know that there's at least one other class that calls
			// this method, it seems like a good time to make some sequences and
			// see if we call the same method on another class. If we do, then
			// we know that this is a decorated thing.
			// DesignParser parser = new DesignParser();
			// // Since the call depth is static, we must reset it to 0, because
			// if
			// // this isn't the first sequence being generated, then it will
			// // already be at its previous max
			// parser.model.setCallDepth(0);
			// // get the size of the sequences, so that we know where to look
			// for
			// // ours (since the sequences variable in Model is static).
			// int seqSize = parser.model.getSequences().size();
			// String[] str = new String[2];
			// // This is the class and method where we want to start the method
			// // tracing
			// str[0] = this.curClassFull.replace("/", ".") + "." + m.getName()
			// + "()";
			// // Call depth of 1
			// str[1] = "15";
			// try {
			// parser.main(str);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// // Inside of this else, we need to make sure we use parser.model,
			// // since we want the model that is stored with the parser
			// IModel tempMod = parser.model;
			// ArrayList<ISequence> seq = model.getSequences();
			// System.out.println("seq");
			if (!DecoratorSpotter.decorates.contains(this.curClass)) {
				DecoratorSpotter.decorates.add(this.curClass);
				this.model.getNamedClass(this.curClass).addClassTypes2(PatternSpotter.DECORATORSTR, "decorator");
			}
		}

		System.out.println("ehre");
	}

	@Override
	public void postVisit(IModel m) {
		super.postVisit(m);
		removeMethsWithOneClass();
		Collection<IClass> classes = this.model.getClasses();
		for (IClass c : classes) {
			IRelation relations = this.model.getRelationsMap().get(c.getName());
			// Get all associations for this class. If there are any
			// associations (we know only associations to classes in the UML are
			// stored), then we know this is an adapter
			ArrayList<String> interfaces = new ArrayList<String>();
			if (relations != null) {
				interfaces = (ArrayList<String>) relations.getInterfaces();
			}

			// if (c.getClassTypes2().containsKey(DECORATORSTR)) {
			// if (!c.getClassTypes2().get(DECORATORSTR).contains("decorator")
			// && c.getClass().getSuperclass() == null
			// && interfaces.size() == 0) {
			System.out.println("c.getClass().getSuperclass(): " + c.getClass().getSuperclass());
			if (!c.getClassTypes2().containsKey(DECORATORSTR)
					&& c.getClass().getSuperclass().toString().contains("java.lang.Object") && interfaces.size() == 0) {
				Collection<IMethod> met = c.getMethods();
				for (IMethod me : met) {
					if (meths.containsKey(me.getName())) {
						this.model.getNamedClass(c.getName()).addClassTypes2(PatternSpotter.DECORATORSTR, "component");
					}
					// }
				}
			}
		}

	}

	private void removeMethsWithOneClass() {
		@SuppressWarnings("unchecked")
		Set<String> keys = DecoratorSpotter.meths.keySet();
		for (String key : keys) {
			if (DecoratorSpotter.meths.get(key).size() == 1) {
				DecoratorSpotter.meths.remove(key);
			}
		}
	}

}
