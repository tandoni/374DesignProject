package TransferToUML.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import TransferToUML.impl.UMLTransferOutputStream;
import TransferToUML.visitor.ITraverser;
import TransferToUML.visitor.IVisitor;
import problem.asm.DesignParser;

public class TransferToUMLApp {

	public static String[] classes = {
			// "analyze.AbstractClassTwoAbstractMethods",
			// "analyze.ClassPrivate",
			// "analyze.ClassWithJustMainMethod",
			// "analyze.ClassWithOneVariable",
			// "analyze.Interface",
			// "analyze.ProtectedClass"

			"analyze.register.Register", "analyze.register.Sale", "analyze.register.Payment"

	};

	public static void main(String[] args) throws IOException {

		DesignParser parser = new DesignParser();

		parser.main(classes);
		OutputStream out = new FileOutputStream("./input_output/TempUML.vz");
		IVisitor writer = new UMLTransferOutputStream(out);
		ITraverser traverser = (ITraverser) parser.model;

		String title = "example";
		out.write("digraph ".getBytes());
		out.write(title.getBytes());
		out.write(" { \nrankdir=BT;\n".getBytes());
		traverser.acceptUML(writer);
		out.write("}".getBytes());
		out.close();
		// UMLGenerator g = new UMLGenerator(title);
		// g.execute();
	}
}
