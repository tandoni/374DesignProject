package problem.spotter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.asm.DesignParser;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.ISequence;

public class DecoratorSpotter extends PatternSpotterDec {
	// This is map where the key is the name of the method, and the Collection
	// are the classes that call this method. If multiple classes call the same
	// method, we should be suspicious that this is a decorator.
	Map<String, Collection<String>> meths = new HashMap<String, Collection<String>>();

	public DecoratorSpotter(IModel model, PatternSpotter spotter) {
		super(model, spotter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(IMethod m) {
		super.visit(m);
		// Add this class to the classes which have the method of the same name
		if (!this.meths.containsKey(m.getName())) {
			this.meths.put(m.getName(), new ArrayList<String>());
		}
		ArrayList<String> list = (ArrayList<String>) this.meths.get(m.getName());
		int size = list.size();
		list.add(this.curClass);
		// If list is empty, then we know that this is isn't able to be
		// decorated, so we simply add the current class as the first class
		// which calls this method.
		if (size == 0) {
			this.meths.put(m.getName(), list);
		} else {
			// add this class to the list of classes that call this method
			this.meths.put(m.getName(), list);
			// Since we know that there's at least one other class that calls
			// this method, it seems like a good time to make some sequences and
			// see if we call the same method on another class. If we do, then
			// we know that this is a decorated thing.
			DesignParser parser = new DesignParser();
			// Since the call depth is static, we must reset it to 0, because if
			// this isn't the first sequence being generated, then it will
			// already be at its previous max
			parser.model.setCallDepth(0);
			String[] str = new String[2];
			// This is the class and method where we want to start the method
			// tracing
			str[0] = this.curClass + m.getName() + "()";
			// Call depth of 1
			str[1] = "1";
			try {
				parser.main(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Inside of this else, we need to make sure we use parser.model,
			// since we want the model that is stored with the parser
			IModel tempMod = parser.model;
			ArrayList<ISequence> seq = model.getSequences();
			System.out.println("seq");
		}

		System.out.println("ehre");
	}

}
