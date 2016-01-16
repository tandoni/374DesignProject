package problem.asm;

import org.objectweb.asm.Type;
import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import problem.impl.Relation;
import problem.impl.Sequence;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;

public class MethodVisitorHelper extends MethodVisitor {

	private IModel model;
	private IClass myClass;
	private ArrayList<String> arguments;
	private ArrayList<ISequence> subMethods = new ArrayList<ISequence>();

	public MethodVisitorHelper(int api, IModel model, MethodVisitor toDecorate, IClass myClass) {
		super(api, toDecorate);
		this.model = model;
		this.myClass = myClass;
		this.arguments = new ArrayList<String>();
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);

		String[] ownerSplit = owner.split("/");

		String[] fieldSplit = getType(desc).split("\\.");

		if (owner.equals(this.myClass.getName())) {
			// create relation for association
			IRelation r = new Relation(ownerSplit[ownerSplit.length - 1]);
			r.addAssociations(fieldSplit[fieldSplit.length - 1]);
			this.model.addRelation(r);
		}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);

		String[] typeSplit = type.split("/");

		// create relation for association
		IRelation r = new Relation(this.myClass.getName());
		r.addAssociations(typeSplit[typeSplit.length - 1]);
		this.model.addRelation(r);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		String[] args = getArgumentsType(desc);

		String[] ownerSplit = owner.split("/");
		// This is where the sequence is added
		ISequence sequence = new Sequence(this.myClass.getName(), ownerSplit[ownerSplit.length - 1], name, args);
		this.model.addSequence(sequence);

		if (!(name.equals("<init>"))) {
			this.subMethods.add(sequence);
		}
	}

	String[] getArgumentsType(String desc) {
		Type[] args = Type.getArgumentTypes(desc);
		String[] argClassList = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			String[] splitArg = arg.split("\\.");

			arg = splitArg[splitArg.length - 1];
			argClassList[i] = arg;
			this.arguments.add(arg);
		}
		return argClassList;
	}

	String getType(String desc) {
		Type type = Type.getType(desc);
		String t = type.getClassName();
		return t;
	}

	public ArrayList<ISequence> getSubMethods() {
		return this.subMethods;
	}
}