package problem.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.impl.Model;
import problem.interfaces.IModel;

public class DesignParser {

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

		for (String className : args) {
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			String name = "";
			String meth = "";
			if (deb) {
				String[] all = className.split("\\.");
				name = all[0] + "." + all[1] + "." + all[2];
				meth = all[3].substring(0, all[3].indexOf("("));
				System.out.println("name is: " + name);
				System.out.println("method is: " + meth);
			}

			if (deb)
				System.out.println("Class name is: " + className);
			ClassReader reader = new ClassReader(className);

			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

			// TODO: add more DECORATORS here in later milestones to accomplish
			// specific tasks
			// specific tasks
			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class

			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
	}
}
