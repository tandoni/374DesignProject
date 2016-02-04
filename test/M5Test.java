import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.PatternSpotter;

public class M5Test {
	private DesignParser parser;
	
//	@Test
//	public final void testAdapter() throws IOException {
//		
//		this.parser = new DesignParser();
//		String[] classess = { "headfirst.adapter.client.App", "headfirst.adapter.client.ToEnumerationAdaptor", 
//							  "headfirst.adapter.lib.LinearTransformer",
//							  "java.util.Iterator", "java.util.Enumeration"
//				 };
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//		
//		IClass app = parser.model.getNamedClass("App");
//		IClass adapter = parser.model.getNamedClass("ToEnumerationAdaptor");
//		IClass trans = parser.model.getNamedClass("LinearTransformer");
//		IClass iter = parser.model.getNamedClass("Iterator");
//		IClass enumer = parser.model.getNamedClass("Enumeration");
//		
//		assertEquals("component", app.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
//		assertEquals("composite", adapter.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
//		assertEquals("composite", trans.getClassTypes2().get(PatternSpotter.COMPOSITESTR));
//		
//		
//	}
	
//	@Test
//	public final void testAdapter2() throws IOException {
//		
//		this.parser = new DesignParser();
//		String[] classess = { "java.awt.event.MouseAdapter", "java.awt.event.MouseListener","java.awt.event.MouseWheelListener", "java.awt.event.MouseMotionListener"};
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//		
//		IClass mouseAdapter = parser.model.getNamedClass("MouseAdapter");
//		IClass mouseListener = parser.model.getNamedClass("MouseListener");
//		IClass mouseWheelListener = parser.model.getNamedClass("MouseWheelListener");
//		IClass mouseMotionListener = parser.model.getNamedClass("MouseMotionListener");
//		
//	}
	
//	@Test
//	public final void testDecoratr() throws IOException {
//		
//		this.parser = new DesignParser();
//		String[] classess = {"java.lang.Object","java.io.OutputStreamWriter","java.io.Writer"};
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//		
//		IClass object = parser.model.getNamedClass("Object");
//		IClass outputStreamWriter = parser.model.getNamedClass("OutputStreamWriter");
//		IClass writer = parser.model.getNamedClass("Writer");
//		
//	}
	
//	@Test
//	public final void testDecoratr2() throws IOException {
//		
//		this.parser = new DesignParser();
//		String[] classess = {"java.lang.Object","java.io.OutputStreamWriter","java.io.Writer"};
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//		
//		IClass object = parser.model.getNamedClass("Object");
//		IClass outputStreamWriter = parser.model.getNamedClass("OutputStreamWriter");
//		IClass writer = parser.model.getNamedClass("Writer");
//		
//	}
	
//	@Test
//	public final void testDecoratr3() throws IOException {
//		
//		this.parser = new DesignParser();
//		String[] classess = {"java.lang.Object","java.io.InputStreamReader","java.io.Reader"};
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//		
//		IClass object = parser.model.getNamedClass("Object");
//		IClass intputStreamReader = parser.model.getNamedClass("IntputStreamReader");
//		IClass reader = parser.model.getNamedClass("Reader");
//		
//	}
	
	@Test
	public final void testDecoratr4() throws IOException {
		
		this.parser = new DesignParser();
		String[] classess = {"problem.z.decorator.Beverage", 
				"problem.z.decorator.CondimentDecorator",
				"problem.z.decorator.DarkRoast",
				"problem.z.decorator.Decaf",
				"problem.z.decorator.Espresso",
				"problem.z.decorator.HouseBlend",
				"problem.z.decorator.Milk", 
				"problem.z.decorator.Mocha",
				"problem.z.decorator.Soy",
				"problem.z.decorator.StarbuzzCoffee",
				"problem.z.decorator.Whip"};
		MyMainApp.classes = classess;
		MyMainApp.main(classess);
		
		IClass beverage = parser.model.getNamedClass("Beverage");
		IClass condimentDecorator = parser.model.getNamedClass("CondimentDecorator");
		IClass darkRoast = parser.model.getNamedClass("DarkRoast");
		IClass decaf = parser.model.getNamedClass("Decaf");
		IClass espresso = parser.model.getNamedClass("Espresso");
		IClass houseBlend = parser.model.getNamedClass("HouseBlend");
		IClass milk= parser.model.getNamedClass("Milk");
		IClass mocha = parser.model.getNamedClass("Mocha");
		IClass soy = parser.model.getNamedClass("Soy");
		IClass coffee = parser.model.getNamedClass("StarbuzzCoffee");
		IClass whip = parser.model.getNamedClass("Whip");
		
	}
	
}
