import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.PatternSpotter;

public class M5Test {
	private DesignParser parser;
	
	@Test
	public final void testAdapter() throws IOException {
		
		this.parser = new DesignParser();
		String[] classess = { "headfirst.adapter.client.App", "headfirst.adapter.client.ToEnumerationAdaptor", 
							  "headfirst.adapter.lib.LinearTransformer",
							  "java.util.Iterator", "java.util.Enumeration"
				 };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);
		
		IClass app = parser.model.getNamedClass("App");
		IClass adapter = parser.model.getNamedClass("ToEnumerationAdaptorr");
		IClass trans = parser.model.getNamedClass("LinearTransformer");
		IClass iter = parser.model.getNamedClass("Iterator");
		IClass enumer = parser.model.getNamedClass("Enumeration");
		
//		assertEquals("component", app.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
//		assertEquals("composite", adapter.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
//		assertEquals("composite", trans.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
		
		
	}
	
	@Test
	public final void testDecorator() throws IOException {
		
		this.parser = new DesignParser();
		String[] classess = { "java.awt.Component", "java.awt.Container", "java.awt.Panel", "java.awt.Window",
				"java.awt.Frame", "javax.swing.JComponent", "javax.swing.JLabel", "javax.swing.JPanel",
				"javax.swing.AbstractButton", "javax.swing.JButton" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);
		
	}
	
}
