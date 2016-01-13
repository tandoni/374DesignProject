package TransferToUML.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import TransferToUML.api.IClass;
import TransferToUML.api.IModel;
import TransferToUML.api.IRelation;
import TransferToUML.visitor.VisitorAdapter;

public class SDOutputStream extends VisitorAdapter {
	private final OutputStream out;

	public SDOutputStream(OutputStream out) throws IOException {
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
	public void visitSperator() {
		// TODO Auto-generated method stub

	}

	// To-do: Ishank: write all of the parsing code here. prbly hard-code
	// this.getClass.indexAt(0).main method to start diagram
	@Override
	public void visit(IModel m) {
		ArrayList<IRelation> relations = (ArrayList<IRelation>) m.getRelations();

		for (IRelation r : relations) {
			String s = "";

			String n = r.getSubClass();

			if (r.getUses() != null) {
				ArrayList<String[]> uses = new ArrayList<String[]>();
				for (String i : r.getUses()) {
					uses.add(i.split("/"));
				}

				for (String[] i : uses) {
					s += "\n" + n + " -> " + i[i.length - 1] + " [arrowhead = \"vee\", style = \"dashed\"];";
				}
			}
			// System.out.println(r.getSuperClass());

			if (s != "") {
				this.write(s);
			}
		}

	}

	@Override
	public void preVisit(IClass c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IClass c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postVisit(IClass c) {
		// TODO Auto-generated method stub
		
	}

}
