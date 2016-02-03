package problem.spotter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.visitor.IVisitor;

public class CompositeSpotter extends PatternSpotterDec {

	public CompositeSpotter(IModel model, IVisitor decoratedd) {
		super(model, decoratedd);
	}

	@Override
	public void visit(IClass c) {
		super.visit(c);

		// get c's superClass : sc
		IRelation rel = this.model.getRelationsMap().get(c.getFullName());
		String sc = rel.getSuperClass();
		if (!(sc == null))
			if (sc.contains("/"))
				sc = sc.split("/")[sc.split("/").length - 1];
		// get all c's associations
		Collection<String> ass = rel.getAssociations();

		// does associations contain the superclass?
		if (ass.contains(sc)) {
			// if yes color c and sc
			this.model.getNamedClass(sc).addClassTypes2(COMPOSITESTR, "component");
			this.model.getNamedClass(c.getName()).addClassTypes2(COMPOSITESTR, "composite");

			ArrayList<String> cNames = (ArrayList<String>) this.model.getClassNames();
			// Iterate through classes, and see if they are composite or leaf by
			// seeing if they extend component
			for (String s : cNames) {
				IRelation r = this.model.getRelationsMap().get(s);
				String thisSupClass = r.getSuperClass();
				Collection<String> assoc = r.getAssociations();
				// If the super class of this string is the component, then it
				// is either a composite or a leaf
				if (thisSupClass.equals(sc)) {
					// If this class also associates with the component, we
					// assume it is a composite
					if (assoc.contains(sc)) {
						this.model.getNamedClass(s).addClassTypes2(COMPOSITESTR, "composite");
					} else {
						this.model.getNamedClass(s).addClassTypes2(COMPOSITESTR, "leaf");
					}
				}
			}
		}
	}

	@Override
	public void postVisit(IModel m) {
		super.visit(m);
		// First, get all of the classes which extend composites, leafs or the
		// component. We have to worry about all of them. Once you find a class
		// that extends
		// one of these, set it to a leaf. If a class was a leaf, but another
		// class extends it, change it to a composite. Don't change the
		// component. THere should only be one component.
		ArrayList<IClass> classes = (ArrayList<IClass>) m.getClasses();
		for (IClass c : classes) {
			IRelation r = this.model.getRelationsMap().get(c.getFullName());
			String supClass = r.getSuperClass();
			// If this class already contains a composite string, we don't need
			// to run this check.
			if (c.getClassTypes2().containsKey(COMPOSITESTR) || supClass == null) {

			} else {
				HashMap<String, String> map = this.model.getNamedClass(supClass).getClassTypes2();
				// If the map (for the superclass) contains a composite mapping,
				// we need to see if it is a leaf. If it is, we need to change
				// it to a composite. In any case, if the superclass is
				// composite pattern, then so is this class.
				if (map.containsKey(COMPOSITESTR)) {
					if (map.get(COMPOSITESTR).equals("leaf")) {
						c.getClassTypes2().put(COMPOSITESTR, "leaf");
						// map.put(COMPOSITESTR, "composite");
					} else if (map.get(COMPOSITESTR).equals("composite")) {
						c.getClassTypes2().put(COMPOSITESTR, "composite");
					} else if (map.get(COMPOSITESTR).equals("component")) {
						ArrayList<String> assocs = (ArrayList<String>) r.getAssociations();
						// If the current class associates with the component,
						// then the current class is a composite (this should've
						// been checked already when visiting each class, but
						// just to make sure)
						if (assocs.contains(supClass)) {
							c.getClassTypes2().put(COMPOSITESTR, "composite");
						} else {
							c.getClassTypes2().put(COMPOSITESTR, "leaf");
						}
					}
				}
			}
		}
		ArrayList<IClass> ponentList = new ArrayList<IClass>();
		// add all classes that are "components" to this list
		for (IClass c : classes) {
			if (c.getClassTypes2().containsKey(COMPOSITESTR)) {
				if (c.getClassTypes2().get(COMPOSITESTR).equals("component")) {
					ponentList.add(c);
				}
			}
		}
		// Only one of the classes in this arraylist can actually be the
		// component. Find it by checking extensions.
		if (ponentList.size() > 1) {
			for (IClass c : ponentList) {
				// others is the ArrayList of component classes which are not
				// the one we're currently at in the for loop.
				ArrayList<IClass> others = (ArrayList<IClass>) ponentList.clone();
				others.remove(c);
				IRelation r = this.model.getRelationsMap().get(c.getFullName());
				String supCS = r.getSuperClass();
				while (supCS != null) {
					IClass supClass = this.model.getNamedClass(supCS);
					if (others.contains(supClass)) {
						c.getClassTypes2().put(COMPOSITESTR, "composite");
					}
					supCS = this.model.getRelationsMap().get(supClass.getFullName()).getSuperClass();
				}
			}
		}
	}

}
