import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.PatternSpotter;

public class M5DecoratorTest {

	private DesignParser parser;

	@Test
	public final void testDecoratr() throws IOException {

		this.parser = new DesignParser();
		String[] classess = { "problem.app.MyMainApp", "problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor",
				"problem.asm.ClassMethodVisitor", "problem.asm.DesignParser", "problem.asm.IClassVisitor",
				"problem.asm.MethodVisitorHelper", "problem.impl.Class", "problem.impl.Field", "problem.impl.Method",
				"problem.impl.Model", "problem.impl.Relation", "problem.impl.SDOutputStream", "problem.impl.Sequence",
				"problem.impl.UMLOutputStream", "problem.interfaces.IClass", "problem.interfaces.IField",
				"problem.interfaces.IMethod", "problem.interfaces.IModel", "problem.interfaces.IRelation",
				"problem.interfaces.ISequence", "problem.spotter.AdapterSpotter", "problem.spotter.DecoratorSpotter",
				"problem.spotter.SingletonSpotter", "problem.spotter.CompositeSpotter", "problem.spotter.PatternSpotter",
				"problem.visitor.ITraverser", "problem.visitor.IVisitor", "problem.visitor.VisitorAdapter"};
		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		 IClass singleton = this.parser.model.getNamedClass("SingletonSpotter");
		 IClass adapter = this.parser.model.getNamedClass("AdapterSpotter");
		 IClass decorator = this.parser.model.getNamedClass("DecoratorSpotter");
		 IClass composite = this.parser.model.getNamedClass("CompositeSpotter");

//				 assertEquals("decorator",
//						 singleton.getClassTypes2().get(PatternSpotter.DECORATORSTR));
				 assertEquals("decorator",
						 adapter.getClassTypes2().get(PatternSpotter.DECORATORSTR));
				 assertEquals("decorator",
						 decorator.getClassTypes2().get(PatternSpotter.DECORATORSTR));
				 assertEquals("decorator",
						 composite.getClassTypes2().get(PatternSpotter.DECORATORSTR));

	}

	@Test
	public final void testDecoratr2() throws IOException {

		this.parser = new DesignParser();
		String[] classess = { "java.lang.Object", "java.io.Reader", "java.io.InputStreamReader" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		// IClass object = parser.model.getNamedClass("Object");
		// IClass intputStreamReader =
		parser.model.getNamedClass("IntputStreamReader");
		IClass reader = parser.model.getNamedClass("Reader");

		//assertEquals("decorator", reader.getClassTypes2().get(PatternSpotter.DECORATORSTR));

	}

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
