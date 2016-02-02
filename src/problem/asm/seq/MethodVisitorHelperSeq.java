package problem.asm.seq;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import problem.asm.DesignParser;
import problem.impl.Relation;
import problem.impl.Sequence;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;

public class MethodVisitorHelperSeq extends MethodVisitor {

	private IModel model;
	private IClass myClass;
	private ArrayList<String> arguments;
	private ArrayList<ISequence> subMethods = new ArrayList<ISequence>();

	public MethodVisitorHelperSeq(int api, IModel model, MethodVisitor toDecorate, IClass myClass) {
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
		// IRelation r = new Relation(this.myClass.getName());
		// // System.out.println("this.model.getCurrentClass(): " +
		// // this.model.getCurrentClass());
		//
		// r.addAssociations(typeSplit[typeSplit.length - 1]);
		// this.model.addRelation(r);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if (this.model.getRecordSeq()) {
			// System.out.println(
			// "visitMethodInsn " + " owner: " + owner + " name: " + name + "
			// desc: " + getArguments(desc));
		}
		// System.out.println("current Class: " + this.model.getCurrentClass());
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		String[] args = getArgumentsType(desc);

		String[] ownerSplit = owner.split("/");

		if (this.model.getRecordSeq() && this.model.getCallDepth() < DesignParser.MAX_CALL_DEPTH) {
			// If the name of the method is init, then we are intitalizing a new
			// class, and need to record that
			if (name.contains("init>")) {
				this.model.addCreatedClass(owner.split("/")[owner.split("/").length - 1]);
			} else {
				// if this isn't a new class, then we add it SDClassNames
				// because we need it the top of the SD diagram (most likely,
				// still need to checkt to make sure)
				String temp = owner.split("/")[owner.split("/").length - 1];
				if (!this.model.getSDClassNames().contains(temp))
					this.model.addSDClassName(temp);
			}
			// System.out.println("adding a sequence");
			// This is where the sequence is added
			ISequence sequence = new Sequence(this.model.getCurrentClass(),
					owner.split("/")[owner.split("/").length - 1], name, getArguments(desc).split(","));
			this.model.addSequence(sequence);
			// Increment the call depth by 1, since we added another Sequence to
			// the SD.
			this.model.callDepthInc();
			// Now we must traverse through this method call.
			if (!(name.equals("<init>"))) {
				DesignParser parser = new DesignParser();
				String str = owner.replace("/", ".") + "." + sequence.getCalledMethod() + "(";
				ArrayList<String> args2 = sequence.getArguments();
				int size = args2.size();
				if (args2.get(0) == "") {
				}
				if (!(size == 1 && args2.get(0) == "")) {
					for (int i = 0; i < size - 1; i++) {
						str = str + args2.get(i) + " arg" + i + ", ";
					}
					str = str + args2.get(size - 1) + " arg";
				}
				str = str + ")";
				String[] start = { str };
				try {
					parser.main(start);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
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

	// Used in sequence generation
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
}