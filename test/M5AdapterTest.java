import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.AdapterSpotter;
import problem.spotter.PatternSpotter;

public class M5AdapterTest {
	private DesignParser parser;

	@Test
	public final void testAdapter() throws IOException {

		this.parser = new DesignParser();
		// String[] classess = { "headfirst.adapter.client.App",
		// "headfirst.adapter.client.ToEnumerationAdaptor",
		// "headfirst.adapter.lib.LinearTransformer",
		// "java.util.Iterator", "java.util.Enumeration"
		// };

		String[] classess = { "problem.z.adapter.IteratorToEnumerationAdapter", "java.util.Enumeration",
				"java.util.Iterator" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		// IClass app = parser.model.getNamedClass("App");
		IClass adapter = parser.model.getNamedClass("IteratorToEnumerationAdapter");
		// IClass trans = parser.model.getNamedClass("LinearTransformer");
		IClass iter = parser.model.getNamedClass("Iterator");
		IClass enumer = parser.model.getNamedClass("Enumeration");

		assertEquals("target", enumer.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));
		assertEquals("adapter", adapter.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));
		assertEquals("adaptee", iter.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));

	}

	@Test
	public final void testAdapter2() throws IOException {

		this.parser = new DesignParser();
		String[] classess = { "java.awt.event.MouseAdapter", "java.awt.event.MouseListener",
				"java.awt.event.MouseWheelListener", "java.awt.event.MouseMotionListener" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		IClass mouseAdapter = parser.model.getNamedClass("MouseAdapter");
		IClass mouseListener = parser.model.getNamedClass("MouseListener");
		IClass mouseWheelListener = parser.model.getNamedClass("MouseWheelListener");
		IClass mouseMotionListener = parser.model.getNamedClass("MouseMotionListener");

	}

}
