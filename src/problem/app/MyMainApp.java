package problem.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import problem.asm.DesignParser;
import problem.impl.SDOutputStream;
import problem.impl.Sequence;
import problem.impl.UMLOutputStream;
import problem.interfaces.IModel;
import problem.interfaces.ISequence;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

// in input_output dir for project
// "C:\Program Files (x86)\Graphviz2.38\bin\dot" -Tpng graph1.gv >
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

		String[] argTemp = { "List<*>" };
		ISequence subM = new Sequence("java.util.Collections", "Collections", "shuffle", argTemp);

		String[] newClasses = null;
		for (int i = 0; i < 50; i++) {
			IModel model = parser.model;
			newClasses = model.getNewClasses(subM, 5);

			parser.main(newClasses);
		}

		traverser2.acceptSequence(writer2, subM, 5);
		out2.close();

		System.out.println("Program written by Ishank Tandon, Max Morgan, and Ruying Chen.");

	}
}
