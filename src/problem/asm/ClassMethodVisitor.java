package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.impl.Method;
import problem.impl.Relation;
import problem.interfaces.IClass;
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

		String methodName = name;
		String methodArgsStr = getArguments(desc);
		String[] methodArgs = methodArgsStr.split(",");
		if (this.model.getStartClass().equals(this.model.getCurrentClass())
				&& this.model.getStartMethodName().equals(methodName)) {
			boolean argsMatch = true;
			if (methodArgs.length != this.model.getStartMethodArgs().length) {
				argsMatch = false;
			}
			if (methodArgs.length == this.model.getStartMethodArgs().length) {
				for (int i = 0; i < methodArgs.length; i++) {
					if (!methodArgs[i].equals(this.model.getStartMethodArgs()[i])) {
						argsMatch = false;
					}
				}
			}
			if (argsMatch) {
				// Now we need to start recording sequences for the SD
				this.model.setRecordSeq(true);
				// public Sequence(String from, String to, String method,
				// String[] args)
				// Sequence seq = new Sequence(this.model.getCurrentClass(), "",
				// )
				// System.out.println("Current Class: " +
				// this.model.getCurrentClass());
				// System.out.println("Start Class: " +
				// this.model.getStartClass());
				// System.out.println(" method: " + name + " start method name:
				// " + this.model.getStartMethodName());
				// System.out.println("getArguments(desc): " +
				// getArguments(desc));
				// System.out.println(
				// "halalell---------------------------------------------------------------------------------------------------------------");
			}
		}

		if (this.model.getRecordSeq() && this.model.getCallDepth() < DesignParser.MAX_CALL_DEPTH) {
			// If the name of the method is init, then we are intitalizing a
			// new
			// class, and need to record that
			if (name.contains("init>")) {
				// this.model.addCreatedClass(owner.split("/")[owner.split("/").length
				// - 1]);
			} else {
				// if this isn't a new class, then we add it SDClassNames
				// because we need it the top of the SD diagram (most
				// likely,
				// still need to checkt to make sure)
				// String temp = owner.split("/")[owner.split("/").length - 1];
				// if (!this.model.getSDClassNames().contains(temp))
				// this.model.addSDClassName(temp);
				// }
				// System.out.println("adding a sequence");
				// This is where the sequence is added
				// ISequence sequence = new
				// Sequence(this.model.getCurrentClass(),
				// owner.split("/")[owner.split("/").length - 1], name,
				// getArguments(desc).split(","));
				// this.model.addSequence(sequence);
				// Increment the call depth by 1, since we added another
				// Sequence to
				// the SD.
				// this.model.callDepthInc();
				// if (!(name.equals("<init>"))) {
				// this.subMethods.add(sequence);
				// }
			}
		}

		this.myClass = this.getBelongedClass();

		IClass namedClass = this.model.getNamedClass(this.myClass.getName());

		// addAccessLevel(access);
		// addReturnType(desc);

		String s = getArguments(desc);
		// System.out.println("splitArgs: " + splitArgs[0]);

		if (s != "") {
			// IRelation r = new Relation(this.myClass.getName());
			// r.addUses(s);
			// this.model.addRelation(r);
			IRelation r2 = new Relation(this.myClass.getFullName());
			r2.addUses(s);
			this.model.addRelation(r2);
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
			String typeSplit = args[i].getClassName().replace(".", "/");
			// String typeSplit = args[i];
			// if (typeSplit.contains("/")) {
			// typeSplit = typeSplit.substring(2, typeSplit.length() - 2);
			// }
			result += typeSplit + ",";
		}
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
