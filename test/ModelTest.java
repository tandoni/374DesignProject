

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.impl.Model;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;

public class ModelTest {

	@Test
	public void test() {
		
		IModel test = new Model();
		
		ArrayList<IClass> classes = new ArrayList<IClass>();
		ArrayList <IRelation> relations = new ArrayList<IRelation>();
		ArrayList<ISequence> sequences = new ArrayList<ISequence>();
		ArrayList<String> createdClasses = new ArrayList<String>();
		ArrayList<String> classNames = new ArrayList<String>();
		
		for (String s : MyMainApp.classes) {
			String[] split = s.split("\\.");
			s = split[split.length - 1];
			classNames.add(s);
		}
		
		assertEquals(classes, test.getClasses());
		assertEquals(relations, test.getRelations());
		assertEquals(sequences, test.getSequences());
		assertEquals(createdClasses, test.getCreatedClasses());
		assertEquals(classNames, test.getClassNames());
		
	}

}
