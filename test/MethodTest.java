

import static org.junit.Assert.*;

import org.junit.Test;

import problem.impl.Method;
import problem.interfaces.IMethod;

public class MethodTest {

	@Test
	public void test() {
		String[] expr = {"test1","test2"};
		IMethod test = new Method(0, "name", "desc", "signature", expr);
		
		assertEquals(0, test.getAccess());
		assertEquals("name", test.getName());
		assertEquals("desc", test.getDescription());
		assertEquals("signature", test.getSignature());
		assertEquals(expr, test.getExceptions());
	}

}
