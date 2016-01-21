package problem.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.ISequence;
import problem.visitor.VisitorAdapter;

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

	}

	@Override
	public void visit(IModel m) {
		List<ISequence> seqs = m.getSequences();
		// For the SD, we need to call getSDClassNames
		ArrayList<String> SDClassNames = m.getSDClassNames();
		List<String> createdClasses = m.getCreatedClasses();
		StringBuilder sb = new StringBuilder();

		for (String c : SDClassNames) {
			String name = c;
			if (!createdClasses.contains(name))
				sb.append(name + ":" + name + "[a]\n");
		}
		for (String s : createdClasses) {
			sb.append(String.format("/%s:%s[a]\n\n", s, s));
		}

		sb.append(SDClassNames.get(0) + ":" + SDClassNames.get(0) + ".main\n");

		for (ISequence s : seqs) {
			String from = s.getFromClass();
			String to = s.getToClass();
			String calledMethod = s.getCalledMethod();
			List<String> args = s.getArguments();

			sb.append(String.format("%s:%s.%s(", from, to, calledMethod));

			for (int i = 0; i < args.size(); i++) {
				sb.append("arg" + i);
				if (i != args.size() - 1)
					sb.append(", ");
			}

			sb.append(")\n");
		}
		this.write(sb.toString());

	}

	@Override
	public void preVisit(IClass c) {

	}

	@Override
	public void visit(IClass c) {

	}

	@Override
	public void postVisit(IClass c) {

	}

}
