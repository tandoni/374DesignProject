package problem.spotter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;

public class AdapterSpotter extends PatternSpotter {
	Collection<IRelation> r;
	// It shouldn't matter that this is static, since we should only have one
	// instance of AdapterSpotter at any time, but it's good to do just to
	// possibly prevent headaches.
	static Map<String, Collection<String>> thisInterfaces = new HashMap<String, Collection<String>>();
	static Map<String, Collection<String>> thisFields = new HashMap<String, Collection<String>>();

	public AdapterSpotter(IModel model, PatternSpotter spotter) {
		super(model, spotter);
		this.r = super.model.getRelations();
	}

	// Visit the methods. If the method is a method of an interface being
	// implemented by this class, and that method uses a field, then this might
	// be an Adapter pattern.
	// @Override
	// public void visit(IMethod m) {
	// if (!thisInterfaces.isEmpty()) {
	//
	// }
	// }

	// @Override
	// public void visit(IField f) {
	// String fieldType2 = f.getDescription();
	// String fieldType1 =
	// f.getDescription().split("/")[f.getDescription().split("/").length - 1];
	// String fieldType = fieldType1.substring(0, fieldType1.length() - 1);
	// if (!thisFields.containsKey(curClass)) {
	// thisFields.put(curClass, new ArrayList<String>());
	// }
	// ArrayList<String> list = (ArrayList<String>) thisFields.get(curClass);
	// list.add(fieldType);
	// thisFields.put(curClass, list);
	// }

	@Override
	public void visit(IClass c) {
		super.visit(c);
		findInterfaces();
		// I'm checking to see if this class has any interfaces (that are in the
		// UML) and if this
		// class has any relations (to classes in this UML)
		if (thisInterfaces.containsKey(c.getName()) && this.model.getRelationsMap().containsKey(c.getName())) {
			if (thisInterfaces.get(c.getName()).size() == 1) {
				// I'm getting the IRelation that holds all relations for this
				// class
				// I'm visiting.
				IRelation relations = this.model.getRelationsMap().get(c.getName());
				// Get all associations for this class. If there are any
				// associations (we know only associations to classes in the UML
				// are
				// stored), then we know this is an adapter
				ArrayList<String> assocs = (ArrayList<String>) relations.getAssociations();
				String extender = relations.getSuperClass();
				if (assocs.size() >= 1 && extender.equals("")) {
					ArrayList<String> interf = (ArrayList<String>) thisInterfaces.get(c.getName());
					Collection<IMethod> adapterMethods = this.model.getNamedClass(c.getName()).getMethods();
					Collection<IMethod> targetMethods = this.model.getNamedClass(interf.get(0)).getMethods();
					ArrayList<String> adapterMethodsList = new ArrayList<String>();
					ArrayList<String> targetMethodsList = new ArrayList<String>();
					for (IMethod adapterMethod : adapterMethods) {
						adapterMethodsList.add(adapterMethod.getName());
					}
					for (IMethod targetMethod : targetMethods) {
						targetMethodsList.add(targetMethod.getName());
					}
					if ((adapterMethodsList.size() - 1) == targetMethodsList.size()) {
						if (adapterMethodsList.containsAll(targetMethodsList)) {

							// The current class we're in is the "adapter"
							this.model.getNamedClass(c.getName()).addClassTypes2(AdapterSpotter.ADAPTERSTR, "adapter");

							// The interface we are trying to emulate is the
							// "target"
							this.model.getNamedClass(interf.get(0)).addClassTypes2(AdapterSpotter.ADAPTERSTR, "target");

							// The class we're taking into the adapter to be
							// used as if it is another class is the "adaptee"
							this.model.getNamedClass(assocs.get(0)).addClassTypes2(AdapterSpotter.ADAPTERSTR,
									"adaptee");
						}
					}
				}
			}
		}
	}

	private void findInterfaces() {
		for (IRelation re : this.r) {
			Collection<String> inters = re.getInterfaces();
			if (!inters.isEmpty()) {
				for (String in : inters) {
					// Put the interface into the interface map, and associate
					// it with the String
					// that is the name of class that is implementing it.
					if (AdapterSpotter.thisInterfaces.containsKey(super.curClass)) {
						thisInterfaces.put(super.curClass, new ArrayList<String>());
					}
					ArrayList<String> list = (ArrayList<String>) thisInterfaces.get(super.curClass);
					if (list == null) {
						list = new ArrayList<String>();
					}
					list.add(in.split("/")[in.split("/").length - 1]);
					thisInterfaces.put(super.curClass, list);
				}
			}
		}
	}

}
