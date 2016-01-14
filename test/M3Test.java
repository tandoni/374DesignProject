
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import TransferToUML.api.IModel;
import TransferToUML.impl.Model;
import TransferToUML.impl.Sequence;

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
		assertEquals("args0", args[0]);
		assertEquals("args1", args[1]);
		assertEquals("args2", args[2]);
	}

	@Test
	public void testAddSequence() {
		m.
	}

}
