//package problem.spotter;
//
//import problem.interfaces.IClass;
//import problem.interfaces.IField;
//import problem.interfaces.IMethod;
//import problem.interfaces.IModel;
//import problem.interfaces.IRelation;
//import problem.interfaces.ISequence;
//import problem.visitor.IVisitor;
//
//public abstract class PatternSpotterInit extends PatternSpotter implements IVisitor {
//	protected IModel model;
//	protected String curClass = "";
//
//	public PatternSpotterInit(IModel model) {
//		this.model = model;
//	}
//
//	public void visit() {
//	}
//
//	public void preVisit(IClass c) {
//	}
//
//	public void visit(IClass c) {
//		this.curClass = c.getName();
//		// We need to know the current class so that we can see if the field is
//		// also this class for various applications.
//	}
//
//	public void postVisit(IClass c) {
//	}
//
//	public void preVisit(IField f) {
//	}
//
//	public void visit(IField f) {
//	}
//
//	public void preVisit(ISequence s) {
//	}
//
//	public void visit(ISequence s) {
//	}
//
//	public void postVisit(ISequence s) {
//	}
//
//	public void postVisit(IField f) {
//	}
//
//	public void preVisit(IMethod m) {
//	}
//
//	public void visit(IMethod m) {
//	}
//
//	public void postVisit(IMethod m) {
//	}
//
//	public void preVisit(IRelation c) {
//	}
//
//	public void visit(IRelation c) {
//	}
//
//	public void postVisit(IRelation c) {
//	}
//
//	public void preVisit(IModel m) {
//	}
//
//	public void visit(IModel m) {
//	}
//
//	public void postVisit(IModel m) {
//	}
//
//	public void visitSperator() {
//	}
//}
