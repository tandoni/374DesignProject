package problem.spotter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;

public class AdapterSpotter extends PatternSpotter {
	Collection<IRelation> r;
	Map<String, Collection<String>> thisInterfaces = new HashMap<String, Collection<String>>();

	public AdapterSpotter(IModel model) {
		super(model);
		this.r = super.model.getRelations();
	}

	// Visit the methods. If the method is a method of an interface being
	// implemented by this class, and that method uses a field, then this might
	// be an Adapter pattern.
	@Override
	public void visit(IMethod m) {
		System.out.println("visitng");
	}

	@Override
	public void visit(IField f) {
		String fieldType2 = f.getDescription();
		String fieldType1 = f.getDescription().split("/")[f.getDescription().split("/").length - 1];
		String fieldType = fieldType1.substring(0, fieldType1.length() - 1);
		if (thisInterfaces.containsKey(fieldType)) {
			System.out.println("victory");
		}
	}

	@Override
	public void visit(IClass c) {
		super.visit(c);
		findInterfaces();
	}

	private void findInterfaces() {
		for (IRelation re : this.r) {
			Collection<String> inters = re.getInterfaces();
			if (!inters.isEmpty()) {
				for (String in : inters) {
					// Put the interface into the interface map, and associate
					// it with the String
					// that is the name of class that is implementing it.
					if (this.thisInterfaces.containsKey(super.curClass)) {
						thisInterfaces.put(super.curClass, new ArrayList<String>());
					}
					ArrayList<String> list = (ArrayList<String>) thisInterfaces.get(super.curClass);
					if (list == null) {
						list = new ArrayList<String>();
					}
					list.add(in.split("/")[in.split("/").length - 1]);
					thisInterfaces.put(super.curClass, list);
				}
				System.out.println("an interface");
			}
		}
	}

}
