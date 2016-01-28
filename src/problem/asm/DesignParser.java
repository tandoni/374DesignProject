package problem.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.seq.ClassDeclarationVisitorSeq;
import problem.asm.seq.ClassFieldVisitorSeq;
import problem.asm.seq.ClassMethodVisitorSeq;
import problem.impl.Model;
import problem.interfaces.IModel;
import problem.spotter.AdapterSpotter;
import problem.spotter.DecoratorSpotter;
import problem.spotter.PatternSpotter;
import problem.spotter.PatternSpotterInit;
import problem.spotter.SingletonSpotter;
import problem.visitor.ITraverser;

public class DesignParser {
	public final static int DEFAULT_CALL_DEPTH = 5;
	public static int CALL_DEPTH = 5;
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
		if (args.length == 1 && args[0].contains("(")) {
			this.CALL_DEPTH = this.DEFAULT_CALL_DEPTH;
		}
		if (args.length == 2 && args[0].contains("(")) {
			this.CALL_DEPTH = Integer.parseInt(args[1]);
		}
		if (args[0].contains("(")) {
			String className;
			System.out.println("This is a Sequence Diagram method call");
			String[] splitArg1 = args[0].split("\\.");
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

			// System.out.println("className: " + className);
			ClassReader reader = new ClassReader(className);

			// make class declaration visitor to get superclass and
			// interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitorSeq(Opcodes.ASM5, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitorSeq(Opcodes.ASM5, decVisitor, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitorSeq(Opcodes.ASM5, fieldVisitor, model);

			// DECORATE Class method visitor with a decorator that detects
			// singletons
			// ClassVisitor singletonVisitor = new SingletonVisitor

			// Tell the Reader to use our (heavily decorated) ClassVisitor
			// to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		} else {
			for (String className : args) {
				this.model.setCurrentClass(className);
				// ASM's ClassReader does the heavy lifting of parsing the
				// compiled
				// Java class
				// System.out.println("currentClass: " +
				// this.model.getCurrentClass());
				// System.out.println("className: " + className);
				ClassReader reader = new ClassReader(className);

				// make class declaration visitor to get superclass and
				// interfaces
				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

				// DECORATE declaration visitor with field visitor
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

				// DECORATE field visitor with method visitor
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

				// DECORATE Class method visitor with a decorator that detects
				// singletons

				// Tell the Reader to use our (heavily decorated) ClassVisitor
				// to
				// visit the class
				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			}
		}
		// Whichever pattern spotter is the first one (the one below this
		// comment), it must extend PatternSpotterInit, while the rest of the
		// pattern spotters must extend PatternSpotterDec
		// PatternSpotter singletonSpotter = new SingletonSpotter(this.model);
		// // Decorate the adapterSpotter with the SingletonSpotter, so that we
		// can
		// // do both visits in one iteration
		// PatternSpotter adapterSpotter = new AdapterSpotter(this.model,
		// singletonSpotter);
		// // The spotterfinder finds the class
		// // PatternSpotter decoratorSpotterFinder = new
		// // DecoratorSpotterFinder(this.model, adapterSpotter);
		// PatternSpotter decoratorSpotter = new DecoratorSpotter(this.model,
		// adapterSpotter);
		// // Visit the pattern spotters here
		// ITraverser traverser = (ITraverser) this.model;
		// traverser.acceptSpotters(decoratorSpotter);
	}

	public int getCallDepth() {
		return CALL_DEPTH;
	}
}
