import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;

public class M4SDTesting {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		DesignParser parser= new DesignParser();
		String[] classes = {"problem.impl.Class"};
		//("problem.asm.Class,Class,accept,IModelVisitor", 5, classes);
		MyMainApp.classes = classes;
		MyMainApp.main(classes);
		parser.main(classes);

		FileReader reader = null;
		reader = new FileReader("input_output/GraphForSDEdit.sd");

		BufferedReader buffer = new BufferedReader(reader);
		
		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		assertTrue(file.contains("Class:Class[a]"));
		assertTrue(file.contains("IModelVisitor:IModelVisitor[a]"));
		assertTrue(file.contains("IField:IField[a]"));
		assertTrue(file.contains("IMethod:IMethod[a]"));
		assertTrue(file.contains("Collection:Collection[a]"));
		
		assertTrue(file.contains("Class:void=IModelVisitor.preVisit(IClass)"));
		assertTrue(file.contains("Class:void=IModelVisitor.visit(IClass)"));
		assertTrue(file.contains("Class:void=IField.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:boolean=Collection.isEmpty()"));
		assertTrue(file.contains("Class:void=IModelVisitor.intermediateVisit(IClass)"));
		assertTrue(file.contains("Class:void=IMethod.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:void=IModelVisitor.postVisit(IClass)"));
	}

}
