package problem.spotter;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public abstract class PatternSpotter implements IVisitor {
	public static final String ADAPTERSTR = "Adapter";
	public static final String DECORATORSTR = "Decorator";
	public static final String COMPOSITESTR = "Composite";

	protected IModel model;
	private IVisitor decorated;
	protected String curClassFull = "";

	public PatternSpotter(IModel model, IVisitor decoratedd) {
		this.decorated = decoratedd;
		this.model = model;
	}

	public PatternSpotter(IModel model2) {
		this.model = model2;
		this.decorated = null;
	}

	/**
	 * Use this to add a decorator to this visitor after this visitor has been
	 * instantiated.
	 * 
	 * @param decorator
	 */
	public void addDecorator(IVisitor decorator) {
		this.decorated = decorator;
	}

	public void visit() {
	}

	public void preVisit(IClass c) {
		if (decorated != null)
			decorated.preVisit(c);
		this.curClassFull = c.getFullName();
		// We need to know the current class so that we can see if the field is
		// also this class for various applications.
	}

	public void visit(IClass c) {
		if (decorated != null)
			decorated.visit(c);
	}

	public void postVisit(IClass c) {
		if (decorated != null)
			decorated.postVisit(c);
	}

	public void preVisit(IField f) {
		if (decorated != null)
			decorated.preVisit(f);
	}

	public void visit(IField f) {
		if (decorated != null)
			decorated.visit(f);
	}

	public void preVisit(ISequence s) {
		if (decorated != null)
			decorated.preVisit(s);
	}

	public void visit(ISequence s) {
		if (decorated != null)
			decorated.visit(s);
	}

	public void postVisit(ISequence s) {
		if (decorated != null)
			decorated.postVisit(s);
	}

	public void postVisit(IField f) {
		if (decorated != null)
			decorated.postVisit(f);
	}

	public void preVisit(IMethod m) {
		if (decorated != null)
			decorated.preVisit(m);
	}

	public void visit(IMethod m) {
		if (decorated != null)
			decorated.visit(m);
	}

	public void postVisit(IMethod m) {
		if (decorated != null)
			decorated.postVisit(m);
	}

	public void preVisit(IRelation c) {
		if (decorated != null)
			decorated.preVisit(c);
	}

	public void visit(IRelation c) {
		if (decorated != null)
			decorated.visit(c);
	}

	public void postVisit(IRelation c) {
		if (decorated != null)
			decorated.postVisit(c);
	}

	public void preVisit(IModel m) {
		if (decorated != null)
			decorated.preVisit(m);
	}

	public void visit(IModel m) {
		if (decorated != null)
			decorated.visit(m);
	}

	public void postVisit(IModel m) {
		if (decorated != null)
			decorated.postVisit(m);
	}

	public void visitSperator() {
		if (decorated != null)
			decorated.visitSperator();
	}
}
