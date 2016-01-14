package problem.visitor;

public interface ITraverser {
	public void acceptUML(IVisitor v);
	public void acceptSequence(IVisitor v);
}
