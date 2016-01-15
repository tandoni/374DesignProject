package problem.visitor;

import problem.interfaces.ISequence;

public interface ITraverser {
	public void acceptUML(IVisitor v);
	public void acceptSequence(IVisitor v, ISequence subMethods, int depth);
}
