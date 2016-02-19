package problem.spotter;

import problem.visitor.ITraverser;

public interface IPatternVisitor {

	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	
}
