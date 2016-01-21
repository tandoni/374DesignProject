package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
//import org.objectweb.asm.Type;

import problem.impl.Field;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;

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
		String curClassName = this.model.getCurrentClass().split("\\.")[this.model.getCurrentClass().split("\\.").length
				- 1];
		String fieldName = desc.split("/")[desc.split("/").length - 1];
		if (fieldName.contains(";")) {
			fieldName = fieldName.substring(0, fieldName.indexOf(";"));
		}
		if (fieldName.equals(curClassName)) {
			this.model.addSingleton(curClassName);
			this.model.getNamedClass(curClassName).setClassType("singleton");
		}
		this.myClass = this.getBelongedClass();

		IClass namedClass = this.model.getNamedClass(this.myClass.getName());
		namedClass.addField(f);

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
