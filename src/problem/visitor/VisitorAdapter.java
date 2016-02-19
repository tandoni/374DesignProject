package problem.visitor;

import java.util.HashMap;
import java.util.Map;

//import problem.interfaces.IField;
//import problem.interfaces.IMethod;
//import problem.interfaces.IModel;
//import problem.interfaces.IRelation;
//import problem.interfaces.ISequence;

public abstract class VisitorAdapter implements IVisitor {
	
	Map<LookupKey, IVisitMethod> keyToCommand = new HashMap<LookupKey, IVisitMethod>();

	@Override
	public void preVisit(ITraverser t){
		this.visitHelper(VisitType.PreVisit, t);
	}
	
	@Override
	public void visit(ITraverser t){
		this.visitHelper(VisitType.Visit, t);
	}
	
	@Override
	public void postVisit(ITraverser t){
		this.visitHelper(VisitType.PostVisit, t);
	}
	
	private void visitHelper(VisitType vType, ITraverser t){
		LookupKey key = new LookupKey(vType, t.getClass());
		IVisitMethod m = this.keyToCommand.get(key);
		if(m != null) {
			m.execute(t);
		}
	}
	
//	@Override
//	public void preVisit(IField f) {
//	}
//
//	@Override
//	public void visit(IField f) {
//	}
//
//	@Override
//	public void postVisit(IField f) {
//	}
//
//	@Override
//	public void preVisit(IMethod m) {
//	}
//
//	@Override
//	public void visit(IMethod m) {
//	}
//
//	@Override
//	public void postVisit(IMethod m) {
//	}
//
//	@Override
//	public void preVisit(IRelation c) {
//	}
//
//	@Override
//	public void visit(IRelation c) {
//	}
//
//	@Override
//	public void postVisit(IRelation c) {
//	}
//
//	@Override
//	public void preVisit(IModel m) {
//	}
//
//	@Override
//	public void postVisit(IModel m) {
//	}
//
//	@Override
//	public void preVisit(ISequence s) {
//	}
//
//	@Override
//	public void visit(ISequence s) {
//	}
//
//	@Override
//	public void postVisit(ISequence s) {
//	}

	// public void visitRelations(IModel m) {
	//
	// }

	// public void visitSuperClasses(IRelation r) {
	//
	// }
	//
	// public void visitInterfaces(IRelation r) {
	//
	// }

}
