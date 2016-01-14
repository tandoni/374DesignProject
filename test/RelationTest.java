

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import problem.impl.Relation;
import problem.interfaces.IRelation;

public class RelationTest {

	@Test
	public void test() {
		
		IRelation test = new Relation("test");
		
		ArrayList<String>interfaces = new ArrayList<String>();
		ArrayList<String> uses = new ArrayList<String>();
		ArrayList<String> associations = new ArrayList<String>();
		
		assertEquals("test", test.getSubClass());
		assertEquals("", test.getSuperClass());
		assertEquals(interfaces, test.getInterfaces());
		assertEquals(uses, test.getUses());
		assertEquals(associations , test.getAssociations());
	}

}
