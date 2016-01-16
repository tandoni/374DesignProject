package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.impl.Class;
import problem.impl.Relation;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;

public class ClassDeclarationVisitor extends ClassVisitor implements IClassVisitor {

	private IModel model;
	private IClass myClass;

	public ClassDeclarationVisitor(int api, IModel model) {
		super(api);
		this.model = model;
		// not necessary to give this.myClass a value, because it will be
		// replace when visits
		this.myClass = null;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		// System.out.println("Class: "+name+" extends "+superName+" implements
		// "+Arrays.toString(interfaces));

		String classType = "normal";
		System.out.println("ClassDeclarationVisitor class name: " + name);
		String[] cname = name.split("/");
		this.model.setCurrentClass(name.replace("/", "."));
		System.out.println("current class: " + this.model.getCurrentClass());
		// Interface
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			classType = "Interface";
		}

		// TODO: Abstract

		String[] nameSplit = name.split("/");
		name = nameSplit[nameSplit.length - 1];
		// this.myClass.addName(name);
		// this.myClass.setClassType(classType);
		this.myClass = new Class(name, classType);

		IRelation r;
		if ((classType == "Interface") && (interfaces.length > 0)) {
			r = new Relation(name, interfaces[0]);
			// r.addSuperClass(name, interfaces[0]);
		} else {
			r = new Relation(name, superName, interfaces);
			// r.addSuperClass(name, superName);
			// r.addInterfaces(name, interfaces);
		}
		// this.myClass.addRelation(r);
		this.model.addRelation(r);
		this.model.addClass(myClass);

		// TODO: construct an internal representation of the class for later use
		// by decorators
		super.visit(version, access, name, signature, superName, interfaces);

	}

	@Override
	public IClass getBelongedClass() {
		return this.myClass;
	}
}
