import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;
import problem.spotter.AdapterSpotter;
import problem.spotter.PatternSpotter;

public class M5UnitTest {

	private DesignParser parser;

	@Test
	public void adapter() throws IOException {
		this.parser = new DesignParser();
		String[] classess = { "testClasses.AdapteeClass", "testClasses.AdapterClass", "testClasses.TargetInterface" };
		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		IClass adaptee = parser.model.getNamedClass("AdapteeClass");
		IClass adapter = parser.model.getNamedClass("AdapterClass");
		IClass target = parser.model.getNamedClass("TargetInterface");

		assertEquals("target", target.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));
		assertEquals("adapter", adapter.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));
		assertEquals("adaptee", adaptee.getClassTypes2().get(AdapterSpotter.ADAPTERSTR));

	}

	@Test
	public void decorator() throws IOException {
		this.parser = new DesignParser();
		String[] classess = { "testClasses.DecoratorDecorator", "testClasses.ComponentInterface",
				"testClasses.ComponentDecorator" };

		MyMainApp.classes = classess;
		MyMainApp.main(classess);

		IClass decorator = parser.model.getNamedClass("DecoratorDecorator");
		IClass component = parser.model.getNamedClass("ComponentInterface");
		IClass decorator2 = parser.model.getNamedClass("ComponentDecorator");

		assertEquals("component", decorator2.getClassTypes2().get(PatternSpotter.DECORATORSTR));
		assertEquals("decorator",decorator.getClassTypes2().get(PatternSpotter.DECORATORSTR));

	}

}
