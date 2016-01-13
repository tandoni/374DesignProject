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
	public void preVisit(IClass c) {
		// To-do: Ishank rewrite the format() method below to the correct thing
		// for pre-visiting a class (might not need to write anything).
		String s = String.format("%s [\nshape=\"record\",\n", c.getName());
		this.write(s);
	}

	@Override
	public void visit(IClass c) {
		// To-do: Ishank rewrite the format() method below to the correct thing
		// for visiting a class (might not need to write anything).
		String s = String.format("%s [\nshape=\"record\",\n", c.getName());
		this.write(s);
	}

	@Override
	public void postVisit(IClass c) {
		// To-do: Ishank rewrite the format() method below to the correct thing
		// for post-visiting a class (might not need to write anything).
		String s = String.format("%s [\nshape=\"record\",\n", c.getName());
		this.write(s);
	}

	@Override
	public void visitSperator() {
		// TODO Auto-generated method stub

	}

	// To-do: Ishank: in the code below, you can see that we inititalize a
	// String s. All of the s += code needs to be re-written for Sequence
	// Diagram syntax. Also, we might have to visit classes here or something,
	// in order to know how to correctly write the SD visit code.
	@Override
	public void visit(IModel m) {
		ArrayList<IRelation> relations = (ArrayList<IRelation>) m.getRelations();

		for (IRelation r : relations) {
			String s = "";

			String n = r.getSubClass();
			// System.out.println(r.getSuperClass());


			if (s != "") {
				this.write(s);
			}
		}

	}

}
