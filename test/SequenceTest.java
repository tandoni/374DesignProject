

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import problem.impl.Sequence;
import problem.interfaces.ISequence;

public class SequenceTest {

	@Test
	public void test() {
		
		String[] args = {"arg0", "arg1"};
		ArrayList<String> arguments = new ArrayList<String>();
		arguments.add("arg0");
		arguments.add("arg1");
		
		ISequence test =new Sequence("from","to","methodName", args);
		
		assertEquals("from", test.getFromClass());
		assertEquals("to", test.getToClass());
		assertEquals("methodName", test.getCalledMethod());
		assertEquals(arguments, test.getArguments());
	}

}
