package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
//import org.objectweb.asm.Type;

import problem.impl.Field;
import problem.impl.Relation;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;

public class ClassFieldVisitor extends ClassVisitor implements IClassVisitor {

	private IModel model;
	private IClass myClass;
	private ClassVisitor decorated;

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IModel model) {
		super(api, decorated);
		this.model = model;
		this.myClass = null;
		this.decorated = decorated;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		// String type = Type.getType(desc).getClassName();
		IField f = new Field(access, name, desc, signature, value);
		// System.out.println(" "+type+" "+ name);

		// if(decorated instanceof IClassVisitor){
		// this.myClass = ((IClassVisitor) decorated).getBelongedClass();
		// }

		String fieldName = desc.split("/")[desc.split("/").length - 1];
		String fieldNameFull = desc;
		if (fieldNameFull.contains(";")) {
			fieldNameFull = fieldNameFull.substring(0, fieldNameFull.indexOf(";"));
		}
		this.myClass = this.getBelongedClass();

		IClass namedClass = this.model.getNamedClass(this.myClass.getName());
		namedClass.addField(f);
		boolean fieldIsClass = false;
		ArrayList<IClass> classes = (ArrayList<IClass>) this.model.getClasses();
		for (IClass c : classes) {
			if (c.getFullName().equals(fieldNameFull))
				fieldIsClass = true;
		}
		// if (this.model.getClassNames().contains(fieldNameFull)) {
		// IRelation r = new Relation(this.myClass.getName());
		// r.addAssociations(fieldName);
		// this.model.addRelation(r);
		IRelation r2 = new Relation(this.myClass.getFullName());
		r2.addAssociations(fieldNameFull);
		this.model.addRelation(r2);
		// }

		return toDecorate;
	}

	@Override
	public IClass getBelongedClass() {
		if (decorated instanceof IClassVisitor) {
			return ((IClassVisitor) decorated).getBelongedClass();
		}
		return this.myClass;
	};

}
