package problem.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import problem.asm.DesignParser;
import problem.impl.SDOutputStream;
import problem.impl.UMLOutputStream;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

// in input_output dir for project
// "C:\Program Files (x86)\Graphviz2.38\bin\dot" -Tpng GraphForGraphViz.gv >
// graph1.png
public class MyMainApp {

	public static String[] classes = {
			// "analyze.AbstractClassTwoAbstractMethods",
			// "analyze.ClassPrivate",
			// "analyze.ClassWithJustMainMethod",
			// "analyze.ClassWithOneVariable", "analyze.Interface",
			// "analyze.ProtectedClass"

			// Used to test Singleton
			"headfirst.singleton.chocolate.ChocolateBoiler", "headfirst.singleton.chocolate.ChocolateController"

			// "java.util.Collections.shuffle(List<T> list)"
			// "problem.asm.DesignParser.main(String[] args)"

			// "analyze.register.Register.checkout(int cashTendered)",
			// "analyze.register.Sale",
			// "analyze.register.Payment"

			// "java.util.Collections.shuffle(List<T> list)"
			// "analyze.register.Register.checkout(int cashTendered)"
			// "problem.asm.DesignParser.main(String[] args)"
			// "analyze.oneone.DataStandardizerApp.main(String[] args)"

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

		// SDEdit

		OutputStream out2 = new FileOutputStream("./input_output/GraphForSDEdit.sd");
		IVisitor writer2 = new SDOutputStream(out2);
		ITraverser traverser2 = (ITraverser) parser.model;

		traverser2.acceptSequence(writer2, 5);
		out2.close();

		System.out.println("Program written by Ishank Tandon, Max Morgan, and Ruying Chen.");

	}
}
