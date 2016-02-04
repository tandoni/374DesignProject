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
			// Test classes
			// "analyze.AbstractClassTwoAbstractMethods",
			// "analyze.ClassPrivate", "analyze.ClassWithJustMainMethod",
			// "analyze.ClassWithOneVariable", "analyze.Interface",
			// "analyze.ProtectedClass"

			// Used to test Singleton
			// "headfirst.singleton.classic.Singleton"
			// "headfirst.singleton.stat.Singleton",
			// "headfirst.singleton.stat.SingletonClient"
			// "problem.z.singleton.ChocolateBoiler",
			// "problem.z.singleton.ChocolateController"

			// "headfirst.singleton.subclass.CoolerSingleton",
			// "headfirst.singleton.subclass.HotterSingleton",
			// "headfirst.singleton.subclass.Singleton",
			// "headfirst.singleton.subclass.SingletonTestDrive"

			// "java.util.Collections.shuffle(List<T> list)"
			// "problem.asm.DesignParser.main(String[] args)"
			// "problem.asm.ClassMethodVisitor.visitMethod(int access, String
			// name, String desc, String signature, String[] exceptions)"

			// "analyze.register.Register.checkout(int cashTendered)",
			// "analyze.register.Sale",
			// "analyze.register.Payment"

			// "problem.spotter.AdapterSpotter.visit(IClass v)", "5"
			// "java.util.Collections.shuffle(List<T> list)", "5"
			// "analyze.register.Register.checkout(int cashTendered)"
			// "problem.asm.DesignParser.main(String[] args)"
			// "analyze.oneone.DataStandardizerApp.main(String[] args)"

			// decorator tests
			// "problem.z.decorator.Milk.cost()", "5"
			// "problem.z.decorator.Mocha.cost()", "5"
			// "problem.z.decorator.CondimentDecorator.getDescription()", "5"
			// "problem.z.decorator.Milk.cost()", "10"
			// "problem.z.decorator.Beverage",
			// "problem.z.decorator.CondimentDecorator",
			// "problem.z.decorator.DarkRoast",
			// "problem.z.decorator.Decaf", "problem.z.decorator.Espresso",
			// "problem.z.decorator.HouseBlend",
			// "problem.z.decorator.Milk", "problem.z.decorator.Mocha",
			// "problem.z.decorator.StarbuzzCoffee",
			// "problem.z.decorator.Whip", "problem.z.decorator.Soy"

			// adapter tests
			// "problem.z.adapter.IteratorToEnumerationAdapter",
			// "java.util.Enumeration", "java.util.Iterator"

			// Composite tests
			// java.awt
			"java.awt.Component", "java.awt.Container", "java.awt.Panel", "java.awt.Window", "java.awt.Frame",
//			"javax.swing.JComponent", "javax.swing.JLabel", "javax.swing.JPanel", "javax.swing.AbstractButton",
//			"javax.swing.JButton"

			// our own project
//			"problem.app.MyMainApp", "problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor",
//			"problem.asm.ClassMethodVisitor", "problem.asm.DesignParser", "problem.asm.IClassVisitor",
//			"problem.asm.MethodVisitorHelper", "problem.impl.Class", "problem.impl.Field", "problem.impl.Method",
//			"problem.impl.Model", "problem.impl.Relation", "problem.impl.SDOutputStream", "problem.impl.Sequence",
//			"problem.impl.UMLOutputStream", "problem.interfaces.IClass", "problem.interfaces.IField",
//			"problem.interfaces.IMethod", "problem.interfaces.IModel", "problem.interfaces.IRelation",
//			"problem.interfaces.ISequence", "problem.spotter.AdapterSpotter", "problem.spotter.DecoratorSpotter",
//			"problem.spotter.SingletonSpotter", "problem.spotter.CompositeSpotter", "problem.spotter.PatternSpotter",
//			"problem.visitor.ITraverser", "problem.visitor.IVisitor", "problem.visitor.VisitorAdapter"

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

		traverser2.acceptSequence(writer2, parser.getCallDepth());
		out2.close();

		System.out.println("Program written by Ishank Tandon, Max Morgan, and Ruying Chen.");

	}
}
