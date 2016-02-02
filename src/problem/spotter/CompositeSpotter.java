package problem.spotter;

import java.util.ArrayList;
import java.util.Collection;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void visit(IClass c) {
		super.visit(c);

		// get c's superClass : sc
		IRelation rel = this.model.getRelationsMap().get(c.getName());
		String sc = rel.getSuperClass();

		// get all c's associations
		Collection<String> ass = rel.getAssociations();

		// dose associations contains sc?
		if (ass.contains(sc)) {
			// if yes color c and sc
			this.model.getNamedClass(sc).addClassTypes2(COMPOSITESTR, "composite");
			this.model.getNamedClass(c.getName()).addClassTypes2(COMPOSITESTR, "composite");

			// also color all classes extends sc
			Map<String, IRelation> rels = this.model.getRelationsMap();
			Iterator it = rels.keySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				ArrayList<String> list = (ArrayList<String>) pair.getValue();
				for (String name : list) {
					String superC = rels.get(name).getSuperClass();
					if (!superC.equals(sc)) 
						this.model.getNamedClass(name).addClassTypes2(COMPOSITESTR, "composite");
				}
			}
		}
	}

}
