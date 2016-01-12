package TransferToUML.impl;

import java.io.IOException;
import java.io.OutputStream;

import TransferToUML.api.IClass;
import TransferToUML.api.IModel;
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

	@Override
	public void visit(IModel m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSperator() {
		// TODO Auto-generated method stub

	}

}
