import static org.junit.Assert.*;

import java.io.BufferedReader;
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
		//DesignParser parser= new DesignParser();
		String[] classes = {"problem.asm.DesignParser.main(String[] args)"};
		//("problem.asm.Class,Class,acceptUML,IVisitor", 5, classes);
		MyMainApp.classes = classes;
		MyMainApp.main(classes);
		//parser.main(classes);

		FileReader reader = null;
		reader = new FileReader("input_output/GraphForSDEdit.sd");

		BufferedReader buffer = new BufferedReader(reader);
		
		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		System.out.println("file : " + file);
		
		assertTrue(file.contains("DesignParser:DesignParser[a]"));
		assertTrue(file.contains("PrintStream:PrintStream[a]"));
		assertTrue(file.contains("String:String[a]"));
		assertTrue(file.contains("StringBuilder:StringBuilder[a]"));
		
		assertTrue(file.contains("DesignParser:DesignParser.main"));
		assertTrue(file.contains("DesignParser:StringBuilder.new(arg0)"));
		assertTrue(file.contains("DesignParser:StringBuilder.append(arg0)"));
		assertTrue(file.contains("DesignParser:StringBuilder.toString(arg0)"));
		assertTrue(file.contains("DesignParser:PrintStream.println(arg0)"));
		assertTrue(file.contains("DesignParser:String.contains(arg0)"));
		
		buffer.close();
		reader.close();
	}

}
