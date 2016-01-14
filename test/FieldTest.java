

import static org.junit.Assert.*;

import org.junit.Test;

import problem.interfaces.IField;
import problem.impl.Field;

public class FieldTest {
	@Test
	public void test() {
		Object obj = new Object();
		IField test = new Field(0, "name", "desc", "signature", obj);
		
		assertEquals(0, test.getAccess());
		assertEquals("name", test.getName());
		assertEquals("desc", test.getDescription());
		assertEquals("signature", test.getSignature());
		assertEquals(obj, test.getValue());
	}

}
