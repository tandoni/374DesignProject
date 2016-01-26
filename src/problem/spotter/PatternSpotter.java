package problem.spotter;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public abstract class PatternSpotter implements IVisitor {
	public void visit() {
	}

	public void preVisit(IClass c) {
	}

	public void visit(IClass c) {
	}

	public void postVisit(IClass c) {
	}

	public void preVisit(IField f) {
	}

	public void visit(IField f) {
	}

	public void preVisit(ISequence s) {
	}

	public void visit(ISequence s) {
	}

	public void postVisit(ISequence s) {
	}

	public void postVisit(IField f) {
	}

	public void preVisit(IMethod m) {
	}

	public void visit(IMethod m) {
	}

	public void postVisit(IMethod m) {
	}

	public void preVisit(IRelation c) {
	}

	public void visit(IRelation c) {
	}

	public void postVisit(IRelation c) {
	}

	public void preVisit(IModel m) {
	}

	public void visit(IModel m) {
	}

	public void postVisit(IModel m) {
	}

	public void visitSperator() {
	}
}
