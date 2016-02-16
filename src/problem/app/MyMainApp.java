package problem.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import problem.asm.DesignParser;
import problem.impl.SDOutputStream;
import problem.impl.UMLOutputStream;
import problem.spotter.AdapterSpotter;
import problem.spotter.CompositeSpotter;
import problem.spotter.DecoratorSpotter;
import problem.spotter.PatternSpotter;
import problem.spotter.SingletonSpotter;
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
			// "java.awt.Component", "java.awt.Container", "java.awt.Panel",
			// "java.awt.Window", "java.awt.Frame",
			// "javax.swing.JComponent", "javax.swing.JLabel",
			// "javax.swing.JPanel", "javax.swing.AbstractButton",
			// "javax.swing.JButton"

			// "problem.z.composite.AbstractSprite",
			// "problem.z.composite.AnimationPanel",
			// "problem.z.composite.AnimatorApp",
			// "problem.z.composite.CircleSprite",
			// "problem.z.composite.CompositeSprite",
			// "problem.z.composite.CompositeSpriteIterator",
			// "problem.z.composite.CrystalBall", "problem.z.composite.ISprite",
			// "problem.z.composite.MainWindow",
			// "problem.z.composite.NullSpriteIterator",
			// "problem.z.composite.RectangleSprite",
			// "problem.z.composite.RectangleTower",
			// "problem.z.composite.SpriteFactory"

			// "C.Users.morganml.Documents.CS.374.Lab2-3-Solution.src.problem.components.Button.java"
			// "Lab2-3-Solution.src.problem.components.Button.java"
			// "Java.util.List"

			// our own project
			// "problem.app.MyMainApp", "problem.asm.ClassDeclarationVisitor",
			// "problem.asm.ClassFieldVisitor",
			// "problem.asm.ClassMethodVisitor", "problem.asm.DesignParser",
			// "problem.asm.IClassVisitor",
			// "problem.asm.MethodVisitorHelper", "problem.impl.Class",
			// "problem.impl.Field", "problem.impl.Method",
			// "problem.impl.Model", "problem.impl.Relation",
			// "problem.impl.SDOutputStream", "problem.impl.Sequence",
			// "problem.impl.UMLOutputStream", "problem.interfaces.IClass",
			// "problem.interfaces.IField",
			// "problem.interfaces.IMethod", "problem.interfaces.IModel",
			// "problem.interfaces.IRelation",
			// "problem.interfaces.ISequence", "problem.spotter.AdapterSpotter",
			// "problem.spotter.DecoratorSpotter",
			// "problem.spotter.SingletonSpotter",
			// "problem.spotter.CompositeSpotter",
			// "problem.spotter.PatternSpotter",
			// "problem.visitor.ITraverser", "problem.visitor.IVisitor",
			// "problem.visitor.VisitorAdapter"

	};

	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("./input_output/input.txt");
		props.load(in);
		in.close();
		Set<Entry<Object, Object>> entrySet = props.entrySet();
		Set<Object> entryKeys = props.keySet();
		Enumeration<Object> enumKey = props.keys();
		Enumeration<Object> enumer = props.elements();
		int i = 0;
		while (enumer.hasMoreElements()) {
			System.out.println(i + ": " + enumKey.nextElement() + ": " + enumer.nextElement());
			i++;
		}

		// create application properties with default
		Properties applicationProps = new Properties(props);

		DesignParser parser = new DesignParser();

		// Easier to add to ArrayList then change to array, this is used for
		// input from file
		ArrayList<String> classez = new ArrayList<String>();

		// Files.readAllBytes() on .class file
		// Read in the path to the folder where we want to get the classes to
		// analyze
		File folder = new File(props.getProperty("Input-Folder", ""));
		File[] files2 = folder.listFiles();
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(files2));
		// Make sure that all directories are traversed so that only files
		// remain
		for (int ind1 = 0; ind1 < files.size(); ind1++) {
			if (files.get(ind1).isDirectory()) {
				files.addAll(new ArrayList<File>(Arrays.asList(files.get(ind1).listFiles())));
				files.remove(ind1);
				ind1--;
			} else if (files.get(ind1).isFile()) {
				FileInputStream inClass = new FileInputStream(files.get(ind1));
			}
		}
		// Add each file (which represents a class) to the list of classes to
		// analyze
		for (File f : files) {
			classez.add(f.toString());
		}

		// This adds the individual classes specified (outside of the path
		// directory for the package) to the arrayList of classes to be analyzed
		String indiClasses = props.getProperty("Input-Classes", "");
		String[] indiClassesSplit = indiClasses.split(",");
		for (String indiClass : indiClassesSplit) {
			classez.add(indiClass.replace(" ", ""));
		}

		// Only load the classes in ASM if its defined in the input
		boolean classLoading = props.getProperty("Phases", "").contains("Class-Loading");
		if (classLoading) {
			// parser.main(classes);
			parser.main(classez.toArray(new String[classez.size()]));
		}

		// Now we need to determine which patterns we need to detect
		String phases = props.getProperty("Phases", "");
		ArrayList<String> patterns = new ArrayList<String>();
		HashMap<String, PatternSpotter> spotterNames = new HashMap<String, PatternSpotter>();
		populateSpotterNames(spotterNames, parser);

		ArrayList<PatternSpotter> activeSpotters = new ArrayList<PatternSpotter>();
		// Iterate through every key in the pattern detection map to see if we
		// should detect any of those patterns. If so, add it to activeSpotters.
		for (String ke : spotterNames.keySet()) {
			if (phases.contains(ke)) {
				activeSpotters.add(spotterNames.get(ke));
			}
		}

		for (int ind = 1; ind < activeSpotters.size(); ind++) {
			activeSpotters.get(ind).addDecorator(activeSpotters.get(ind - 1));
		}
		ITraverser patternTraverser = (ITraverser) parser.model;
		patternTraverser.acceptSpotters(activeSpotters.get(activeSpotters.size() - 1));

		// OutputStream out = new
		// FileOutputStream("./input_output/GraphForGraphViz.gv");
		OutputStream out = new FileOutputStream(props.getProperty("Output-Directory"));
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

	/**
	 * Populates the map that matches the String in the properties input field
	 * to the correct spotter class to detect that pattern. The key should be
	 * exactly what is in the input Properties file.
	 * 
	 * @param spotterNames
	 * @param parser
	 */
	private static void populateSpotterNames(HashMap<String, PatternSpotter> spotterNames, DesignParser parser) {
		spotterNames.put("Singleton-Detection", new SingletonSpotter(parser.model));
		spotterNames.put("Decorator-Detection", new DecoratorSpotter(parser.model));
		spotterNames.put("Adapter-Detection", new AdapterSpotter(parser.model));
		spotterNames.put("Composite-Detection", new CompositeSpotter(parser.model));
	}
}
