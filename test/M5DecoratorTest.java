import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.PatternSpotter;

public class M5DecoratorTest {

	private DesignParser parser;

//	@Test
//	public final void testDecoratr() throws IOException {
//
//		this.parser = new DesignParser();
//		String[] classess = { "problem.spotter.AdapterSpotter", "problem.spotter.DecoratorSpotter",
//				"problem.spotter.SingletonSpotter", "problem.spotter.CompositeSpotter",
//				"problem.spotter.PatternSpotter", "problem.spotter.PatternSpotterDec",
//				"problem.spotter.PatternSpotterInit", };
//		MyMainApp.classes = classess;
//		MyMainApp.main(classess);
//
//		// IClass object = parser.model.getNamedClass("Object");
//		// IClass outputStreamWriter =
//		// parser.model.getNamedClass("OutputStreamWriter");
//		// IClass writer = parser.model.getNamedClass("Writer");
//
//	}

	// @Test
	// public final void testDecoratr2() throws IOException {
	//
	// this.parser = new DesignParser();
	// String[] classess =
	// {"java.lang.Object","java.io.Reader","java.io.InputStreamReader"};
	// MyMainApp.classes = classess;
	// MyMainApp.main(classess);
	//
	//// IClass object = parser.model.getNamedClass("Object");
	//// IClass intputStreamReader =
	// parser.model.getNamedClass("IntputStreamReader");
	// IClass reader = parser.model.getNamedClass("Reader");
	//
	// assertEquals("decorator",
	// reader.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	//
	// }

	 @Test
	 public final void testDecoratr3() throws IOException {
	
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
	 IClass condimentDecorator =
	 parser.model.getNamedClass("CondimentDecorator");
	// IClass darkRoast = parser.model.getNamedClass("DarkRoast");
	// IClass decaf = parser.model.getNamedClass("Decaf");
	// IClass espresso = parser.model.getNamedClass("Espresso");
	// IClass houseBlend = parser.model.getNamedClass("HouseBlend");
	 IClass milk= parser.model.getNamedClass("Milk");
	 IClass mocha = parser.model.getNamedClass("Mocha");
	 IClass soy = parser.model.getNamedClass("Soy");
	// IClass coffee = parser.model.getNamedClass("StarbuzzCoffee");
	 IClass whip = parser.model.getNamedClass("Whip");
	
	 assertEquals("component",
	 beverage.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	 assertEquals("decorator",
	 condimentDecorator.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	 assertEquals("decorator",
	 milk.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	 assertEquals("decorator",
	 mocha.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	 assertEquals("decorator",
	 soy.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	 assertEquals("decorator",
	 whip.getClassTypes2().get(PatternSpotter.DECORATORSTR));
	
	 }

}
