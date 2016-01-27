package problem.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.impl.Model;
import problem.interfaces.IModel;
import problem.spotter.AdapterSpotter;
import problem.spotter.PatternSpotter;
import problem.spotter.SingletonSpotter;
import problem.visitor.ITraverser;

public class DesignParser {
	public final static int CALL_DEPTH = 20;
	public IModel model;
	boolean deb = false;

	public DesignParser() {
		this.model = new Model();
	}

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 * 
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public void main(String[] args) throws IOException {
		System.out.println("args: " + args);
		for (String className : args) {
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			// System.out.println("currentClass: " +
			// this.model.getCurrentClass());
			if (args.length == 1 && className.contains("(")) {
				System.out.println("This is a Sequence Diagram method call");
				String[] splitArg1 = className.split("\\.");
				int splitArg1len = splitArg1.length;
				this.model.setStartMethod(splitArg1[splitArg1len - 1]);
				System.out.println("startMethodName: " + this.model.getStartMethodName());
				System.out.println("startMethodArgs[0]: " + this.model.getStartMethodArgs()[0]);

				// Since the className also contained the method, we must get
				// rid of the method part
				className = "";
				for (int i = 0; i < splitArg1len - 2; i++) {
					className += splitArg1[i];
					className += ".";
				}
				className += splitArg1[splitArg1len - 2];
				if (className.contains("\\.")) {
					this.model.addSDClassName(className.split("\\.")[2]);
				} else {
					this.model.addSDClassName(className);
				}
				this.model.setStartClass(className);
			}
			this.model.setCurrentClass(className);
			// System.out.println("className: " + className);
			ClassReader reader = new ClassReader(className);

			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

			// DECORATE Class method visitor with a decorator that detects
			// singletons
			// ClassVisitor singletonVisitor = new SingletonVisitor

			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		PatternSpotter singletonSpotter = new SingletonSpotter(this.model);
		// Decorate the adapterSpotter with the SingletonSpotter, so that we can
		// do both visits in one iteration
		PatternSpotter adapterSpotter = new AdapterSpotter(this.model);
		// Visit the pattern spotters here
		ITraverser traverser = (ITraverser) this.model;
		traverser.acceptSpotters(singletonSpotter);
		// traverser.acceptSpotters(adapterSpotter);
	}
}
