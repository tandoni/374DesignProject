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
			"analyze.AbstractClassTwoAbstractMethods",
			"analyze.ClassPrivate",
			"analyze.ClassUsesClass",
			"analyze.ClassWithJustMainMethod",
			"analyze.ClassWithOneVariable",
			"analyze.Interface",
			"analyze.ProtectedClass"
	};

	public static void main(String[] args) throws IOException {	
		
		DesignParser parser = new DesignParser();
		
		parser.main(classes);
		OutputStream out = new FileOutputStream("./input_output/TempUML.dot");
		IVisitor writer = new UMLTransferOutputStream(out);
		ITraverser traverser = (ITraverser) parser.model;
		
		String title = "example";
		out.write("digraph ".getBytes());
		out.write(title.getBytes());
		out.write(" { \nrankdir=BT;\n".getBytes());
		traverser.accept(writer);
		out.write("}".getBytes());
		out.close();
		//UMLGenerator g = new UMLGenerator(title);
		//g.execute();
	}
}
