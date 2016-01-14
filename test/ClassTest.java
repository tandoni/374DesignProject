

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IRelation;
import problem.impl.Class;

public class ClassTest {

	@Test
	public void test() {
		IClass test = new Class("test");
		Collection<IMethod> methods = new ArrayList<IMethod>();
		Collection<IField> fields = new ArrayList<IField>();
		Collection<IRelation> relations = new ArrayList<IRelation>();
		
		assertEquals("test", test.getName());
		assertEquals(methods, test.getMethods());
		assertEquals(fields, test.getFields());
		assertEquals(relations, test.getRelations());
		assertEquals("normal", test.getClassType());
		
	}

}
