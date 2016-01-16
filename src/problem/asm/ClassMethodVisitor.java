package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.impl.Method;
import problem.impl.Relation;
import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;

public class ClassMethodVisitor extends ClassVisitor implements IClassVisitor {

	private IModel model;
	private IClass myClass;
	private ClassVisitor decorated;

	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IModel model) {
		super(api, decorated);
		this.model = model;
		this.myClass = null;
		this.decorated = decorated;
	}

	// access = protected, private, etc. for method. name is name of method.
	// desc is the description (return type, and other information).
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		// System.out.println(" method " + name);

		this.myClass = this.getBelongedClass();

		IClass namedClass = this.model.getNamedClass(this.myClass.getName());

		// addAccessLevel(access);
		// addReturnType(desc);

		String[] splitArgs = getArguments(desc).split(",");

		for (String s : splitArgs) {
			if (s != "") {
				IRelation r = new Relation(this.myClass.getName());
				r.addUses(s);
				this.model.addRelation(r);
			}
		}

		MethodVisitor newToDecorate = new MethodVisitorHelper(Opcodes.ASM5, this.model, toDecorate, this.myClass);
		// getSubMethods() will get the method for the sequence.
		Method m = new Method(access, name, desc, signature, ((MethodVisitorHelper) newToDecorate).getSubMethods());
		namedClass.addMethod(m);

		return newToDecorate;

	}
	//
	// void addAccessLevel(int access){
	// String level="";
	// if((access&Opcodes.ACC_PUBLIC)!=0){
	// level="public";
	// }else if((access&Opcodes.ACC_PROTECTED)!=0){
	// level="protected";
	// }else if((access&Opcodes.ACC_PRIVATE)!=0){
	// level="private";
	// }else{
	// level="default";
	// }
	// //System.out.println(" access level: "+level);
	// // TODO: ADD this information to your representation of the current
	// method.
	// }
	//
	// void addReturnType(String desc){
	// String returnType = Type.getReturnType(desc).getClassName();
	// //System.out.println(" return type: " + returnType);
	// // TODO: ADD this information to your representation of the current
	// method.
	// }
	//
	// void addArguments(String desc){
	// Type[] args = Type.getArgumentTypes(desc);
	// for(int i=0; i< args.length; i++){
	// String arg=args[i].getClassName();
	// //System.out.println(" arg "+i+": "+arg);
	// // TODO: ADD this information to your representation of the current
	// method.
	// }
	// }

	String getArguments(String desc) {

		String result = "";

		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String[] typeSplit = args[i].getClassName().split("\\.");
			result += typeSplit[typeSplit.length - 1] + ",";
		}
		if (result != "")
			result = result.substring(0, result.length() - 2);
		return result;
	}

	@Override
	public IClass getBelongedClass() {
		if (decorated instanceof IClassVisitor) {
			return ((IClassVisitor) decorated).getBelongedClass();
		}
		return this.myClass;
	};
}
