package TransferToUML.visitor;

import TransferToUML.api.IRelation;
import TransferToUML.api.IField;
import TransferToUML.api.IMethod;
import TransferToUML.api.IModel;

public abstract class VisitorAdapter implements IVisitor {

	@Override
	public void preVisit(IField f) {
	}

	@Override
	public void visit(IField f) {
	}

	@Override
	public void postVisit(IField f) {
	}

	@Override
	public void preVisit(IMethod m) {
	}

	@Override
	public void visit(IMethod m) {
	}

	@Override
	public void postVisit(IMethod m) {
	}

	@Override
	public void preVisit(IRelation c) {
	}

	@Override
	public void visit(IRelation c) {
	}

	@Override
	public void postVisit(IRelation c) {
	}
	
	@Override
	public void preVisit(IModel m) {
	}

	@Override
	public void postVisit(IModel m) {
	}

//	public void visitRelations(IModel m) {
//		
//	}

//	public void visitSuperClasses(IRelation r) {
//
//	}
//
//	public void visitInterfaces(IRelation r) {
//
//	}

}
