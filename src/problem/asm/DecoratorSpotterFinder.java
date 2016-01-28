package problem.asm;

import problem.interfaces.IModel;
import problem.spotter.PatternSpotterDec;
import problem.visitor.IVisitor;

public class DecoratorSpotterFinder extends PatternSpotterDec {

	public DecoratorSpotterFinder(IModel model, IVisitor decoratedd) {
		super(model, decoratedd);
		// TODO Auto-generated constructor stub
	}

}
