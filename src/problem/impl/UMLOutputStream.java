package problem.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.visitor.VisitorAdapter;

public class UMLOutputStream extends VisitorAdapter {
	private final OutputStream out;

	public UMLOutputStream(OutputStream out) throws IOException {
		super();
		this.out = out;
	}

	private void write(String s) {
		try {
			this.out.write(s.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void preVisit(IClass c) {
		String s = String.format("%s [\nshape=\"record\",\n", c.getName());
		this.write(s);
	}

	@Override
	public void visit(IClass c) {
		String s;
		if (c.getClassType().equalsIgnoreCase("Interface")) {
			s = String.format("color=\"red\",label = \"{\\<\\<interface\\>\\>\\n%s| ", c.getName());
		} else if (c.getClassType().equalsIgnoreCase("Abstract")) {
			s = String.format("color=\"green\",label = \"{\\<\\<Abstract\\>\\>\\n%s| ", c.getName());
		} else if (c.getClassType().equalsIgnoreCase("Singleton")) {
			// To-DO Ishank please format this so that the class name comes
			// first and <<singleton>> comes below it. Look at M4 for example.
			// Also, somewhere we need to color the box around the Singleton
			// class a different color. You can probably do that here, but if
			// not just see if c.getClassType() == "Singleton". If it does, then
			// do it there.
			s = String.format("color=\"blue\", label = \"{%s\n\\<\\<Singleton\\>\\>| ", c.getName());
		} else {
			s = String.format("label = \"{%s| ", c.getName());
		}

		this.write(s);
	}

	@Override
	public void postVisit(IClass c) {
		String s = "}\"\n];\n\n";
		this.write(s);
	}

	@Override
	public void visit(IField f) {
		String type = Type.getType(f.getDescription()).getClassName();
		addAccessLevel(f.getAccess());
		addColon(f.getName());
		String[] typeSplit = type.split("\\.");

		this.write(typeSplit[typeSplit.length - 1] + "\\l");

	}

	@Override
	public void visit(IMethod m) {

		if (!m.getName().contains("<init>")) {
			addAccessLevel(m.getAccess());
			this.write(m.getName() + "(");
			addArguments(m.getDescription());
			this.write(") : ");
			addReturnType(m.getDescription());
			this.write("\\l");
		}
	}

	// not draw the relation after every class but draw them all together after
	// draw classes

	@Override
	public void visit(IModel m) {
		ArrayList<IRelation> relations = (ArrayList<IRelation>) m.getRelations();

		// String comment = "//begins writing relations";
		// this.write(comment);

		// for (IRelation r : relations) {
		// this.visitSuperClasses(r);
		// }
		//
		// for (IRelation r : relations) {
		// this.visitInterfaces(r);
		// }

		for (IRelation r : relations) {
			String s = "";

			String n = r.getSubClass();
			// System.out.println(r.getSuperClass());
			if (r.getSuperClass() != null) {
				String[] superClass = r.getSuperClass().split("/");
				s += "\n" + n + " -> " + superClass[superClass.length - 1] + " [arrowhead=\"empty\"];";
			}

			ArrayList<String[]> interfaces = new ArrayList<String[]>();
			if (r.getInterfaces() != null) {
				for (String i : r.getInterfaces()) {
					interfaces.add(i.split("/"));
				}

				for (String[] i : interfaces) {
					s += "\n" + n + " -> " + i[i.length - 1] + " [arrowhead=\"empty\", style=\"dashed\"];";
				}
			}

			ArrayList<String[]> uses = new ArrayList<String[]>();
			if (r.getUses() != null) {
				for (String i : r.getUses()) {
					uses.add(i.split("/"));
				}

				for (String[] i : uses) {
					s += "\n" + n + " -> " + i[i.length - 1] + " [arrowhead = \"vee\", style = \"dashed\"];";
				}
			}

			ArrayList<String[]> ass = new ArrayList<String[]>();
			if (r.getAssociations() != null) {
				for (String i : r.getAssociations()) {
					ass.add(i.split("/"));
				}

				for (String[] i : ass) {
					s += "\n" + n + " -> " + i[i.length - 1] + " [arrowhead = \"vee\"];";
				}
			}

			if (s != "") {
				this.write(s);
			}
		}

	}

	@Override
	public void postVisit(IRelation r) {
		super.postVisit(r);
	}

	void addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+ ";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "# ";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "- ";
		} else {
			level = "  ";
		}

		if (level != "") {
			this.write(level);
		}
	}

	void addReturnType(String desc) {
		String[] typeSplit = Type.getReturnType(desc).getClassName().split("\\.");

		this.write(typeSplit[typeSplit.length - 1]);
	}

	void addArguments(String desc) {
		Type[] args = Type.getArgumentTypes(desc);

		if (args.length > 0) {
			String[] typeSplit = args[0].getClassName().split("\\.");
			this.write(typeSplit[typeSplit.length - 1] + " ");
			for (int i = 1; i < args.length - 1; i++) {
				typeSplit = args[i].getClassName().split("\\.");
				this.write(", " + typeSplit[typeSplit.length - 1]);
			}
		}

	}

	private void addColon(String name) {
		this.write(name + " : ");
	}

	@Override
	public void visitSperator() {
		String s = "|";
		this.write(s);
	}

}
