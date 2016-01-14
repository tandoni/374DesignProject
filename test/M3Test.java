
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import problem.impl.Model;
import problem.impl.Sequence;
import problem.interfaces.IModel;
import problem.interfaces.ISequence;

public class M3Test {
	IModel m = new Model();
	Sequence seq = new Sequence();
	String[] args = new String[3];
	Sequence seq2 = new Sequence();
	String[] args2 = new String[2];

	public M3Test() {

		args[0] = "args0";
		args[1] = "args1";
		args[2] = "args2";
		seq.setCalledMethod("method");
		seq.addFromClass("fromClass");
		seq.addToClass("toClass");
		seq.addArguments(args);

		args2[0] = "arg20";
		args2[1] = "args21";
		seq2.setCalledMethod("method2");
		seq2.addFromClass("fromClass2");
		seq2.addToClass("toClass2");
		seq2.addArguments(args2);
	}

	@Test
	public void testSeqGetterSetter() {
		assertEquals("method", seq.getCalledMethod());
		assertEquals("fromClass", seq.getFromClass());
		assertEquals("toClass", seq.getToClass());
		assertEquals("args0", seq.getArguments().get(0));
		assertEquals("args1", seq.getArguments().get(1));
		assertEquals("args2", seq.getArguments().get(2));
	}

}
