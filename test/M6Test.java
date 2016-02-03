import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.PatternSpotter;

public class M6Test {
	private DesignParser parser;
	private IClass component;
	private IClass container;
	private IClass panel;
	private IClass window;
	private IClass frame;
	private IClass Jcomponent;
	private IClass Jlabel;
	private IClass Jpanel;
	private IClass abstractbutton;
	private IClass Jbutton;

	@Before
	public void setUp() throws Exception {
		this.parser = new DesignParser();
		String[] classess = { "java.awt.Component", "java.awt.Container", "java.awt.Panel", "java.awt.Window",
				"java.awt.Frame", "javax.swing.JComponent", "javax.swing.JLabel", "javax.swing.JPanel",
				"javax.swing.AbstractButton", "javax.swing.JButton" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);
		component = parser.model.getNamedClass("Component");
		container = parser.model.getNamedClass("Container");
		panel = parser.model.getNamedClass("Panel");
		window = parser.model.getNamedClass("Window");
		frame = parser.model.getNamedClass("Frame");
		this.Jcomponent = parser.model.getNamedClass("JComponent");
		this.Jlabel = parser.model.getNamedClass("JLabel");
		this.Jpanel = parser.model.getNamedClass("JPanel");
		this.abstractbutton = parser.model.getNamedClass("AbstractButton");
		this.Jbutton = parser.model.getNamedClass("JButton");
	}

	@Test
	public final void testModel() throws IOException {
		// Make sure the classes are the correct thing of the composite pattern.
		// (java.awt)
		assertEquals("component", component.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("composite", container.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("composite", panel.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("composite", window.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("composite", frame.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		// (javax.swing tests)
		assertEquals("composite", Jcomponent.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("composite", Jlabel.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("leaf", Jpanel.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("leaf", Jbutton.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		assertEquals("leaf", abstractbutton.getClassTypes2().get(PatternSpotter.COMPOSITESTR));

	}

	@Test
	public final void testParser() {

	}
}
