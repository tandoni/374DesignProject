package problem.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import problem.asm.DesignParser;
import problem.impl.SDOutputStream;
import problem.impl.UMLOutputStream;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

public class MyMainApp {

	public static String[] classes = {
			// "analyze.AbstractClassTwoAbstractMethods",
			// "analyze.ClassPrivate",
			// "analyze.ClassWithJustMainMethod",
			// "analyze.ClassWithOneVariable",
			// "analyze.Interface",
			// "analyze.ProtectedClass"
//			"java.util.Collections.shuffle(List<T> list)"
			"analyze.register.Register", "analyze.register.Sale", "analyze.register.Payment"

	};

	public static void main(String[] args) throws IOException {

		DesignParser parser = new DesignParser();

		parser.main(classes);
		OutputStream out = new FileOutputStream("./input_output/GraphForGraphViz.gv");
		IVisitor writer = new UMLOutputStream(out);
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
		
		OutputStream out2 = new FileOutputStream("./input_output/GraphForSDEdit.sd");
		IVisitor writer2 = new SDOutputStream(out2);
		ITraverser traverser2 = (ITraverser) parser.model;
		
		traverser2.acceptSequence(writer2);
		out2.close();
		
		System.out.println("Program written by Ishank Tandon, Max Morgan, and Ruying Chen.");
		
	}
}
