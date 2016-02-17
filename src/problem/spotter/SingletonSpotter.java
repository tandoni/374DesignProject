package problem.spotter;

import problem.interfaces.IField;
import problem.interfaces.IModel;

public class SingletonSpotter extends PatternSpotter {

	// @Override
	// public void visit(IClass c) {
	// // We need to know the current class so that we can see if the field is
	// // also this class.
	// this.curClass = c.getName();
	// System.out.println("curClass: " + curClass);
	// }

	public SingletonSpotter(IModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	public SingletonSpotter(IModel model, PatternSpotter decorated) {
		super(model, decorated);
	}

	// When visit is called for a spotter, the spotter then spots the design
	// pattern and makes the necessary changes to the Model class.
	@Override
	public void visit(IField f) {
		super.visit(f);
		// The check to see if a private static instance of the class is
		// instantiated inside of itself (means this is a singleton)
		// System.out.println("f.getDescription(): " + f.getDescription());
		String fieldType = f.getDescription();
		if (fieldType.contains(";"))
			fieldType = fieldType.substring(1, fieldType.length() - 1);
		if (super.curClassFull.equals(fieldType) && f.getAccess() == 10) {
			super.model.addSingleton(this.curClassFull);
			this.model.getNamedClass(this.curClassFull).setClassType("singleton");
		}
		// System.out.println("here");
	}

}
