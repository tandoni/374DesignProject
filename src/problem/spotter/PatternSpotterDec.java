package problem.spotter;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public abstract class PatternSpotterDec extends PatternSpotter implements IVisitor {
	protected IModel model;
	private IVisitor decorated;
	protected String curClass = "";

	public PatternSpotterDec(IModel model, IVisitor decoratedd) {
		this.decorated = decoratedd;
		this.model = model;
	}

	public void visit() {
	}

	public void preVisit(IClass c) {
		decorated.preVisit(c);
		this.curClass = c.getName();
		// We need to know the current class so that we can see if the field is
		// also this class for various applications.
	}

	public void visit(IClass c) {
		decorated.visit(c);
	}

	public void postVisit(IClass c) {
		decorated.postVisit(c);
	}

	public void preVisit(IField f) {
		decorated.preVisit(f);
	}

	public void visit(IField f) {
		decorated.visit(f);
	}

	public void preVisit(ISequence s) {
		decorated.preVisit(s);
	}

	public void visit(ISequence s) {
		decorated.visit(s);
	}

	public void postVisit(ISequence s) {
		decorated.postVisit(s);
	}

	public void postVisit(IField f) {
		decorated.postVisit(f);
	}

	public void preVisit(IMethod m) {
		decorated.preVisit(m);
	}

	public void visit(IMethod m) {
		decorated.visit(m);
	}

	public void postVisit(IMethod m) {
		decorated.postVisit(m);
	}

	public void preVisit(IRelation c) {
		decorated.preVisit(c);
	}

	public void visit(IRelation c) {
		decorated.visit(c);
	}

	public void postVisit(IRelation c) {
		decorated.postVisit(c);
	}

	public void preVisit(IModel m) {
		decorated.preVisit(m);
	}

	public void visit(IModel m) {
		decorated.visit(m);
	}

	public void postVisit(IModel m) {
		decorated.postVisit(m);
	}

	public void visitSperator() {
		decorated.visitSperator();
	}
}
