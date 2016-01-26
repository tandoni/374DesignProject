package problem.visitor;

public interface ITraverser {
	// This is used to accept the pattern spotters, who will traverse the model
	// (much like the UML and Sequence generators) and will find patterns in the
	// code.
	public void acceptSpotters(IVisitor v);

	public void acceptUML(IVisitor v);

	public void acceptSequence(IVisitor v, int depth);
}
