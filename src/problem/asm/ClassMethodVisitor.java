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
			}
		}

		String fieldName = desc;
		if (fieldName.contains(";")) {
			fieldName = fieldName.substring(1, fieldName.indexOf(";"));
		}
		this.myClass = this.getBelongedClass();

		IClass namedClass = this.model.getNamedClass(this.myClass.getName());
		boolean isAClass = false;
		for (IClass clas : this.model.getClasses()) {
			if (clas.getFullName().contains(fieldName))
				isAClass = true;
		}
		if (isAClass) {
			// IRelation r = new Relation(this.myClass.getName());
			// r.addAssociations(fieldName);
			// this.model.addRelation(r);
			IRelation r2 = new Relation(this.myClass.getFullName());
			r2.addUses(fieldName);
			this.model.addRelation(r2);
		}

		MethodVisitor newToDecorate = new MethodVisitorHelper(Opcodes.ASM5, this.model, toDecorate, this.myClass);
		// getSubMethods() will get the method for the sequence.
		Method m = new Method(access, name, desc, signature, ((MethodVisitorHelper) newToDecorate).getSubMethods());
		namedClass.addMethod(m);

		return newToDecorate;

	}

	String getArguments(String desc) {

		String result = "";

		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String[] typeSplit = args[i].getClassName().split("\\.");
			result += typeSplit[typeSplit.length - 1] + ",";
		}
		if (result != "")
			result = result.substring(0, result.length() - 1);
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
