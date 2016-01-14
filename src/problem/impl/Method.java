package problem.impl;

import problem.interfaces.IMethod;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

public class Method implements IMethod {
	private int access;
	private String name;
	private String description;
	private String signature;
	private String[] exceptions;

	public Method(int access, String name, String desc, String signature, String[] exceptions) {
		this.access = access;
		this.name = name;
		this.description = desc;
		this.signature = signature;
		this.exceptions = exceptions;
	}

	@Override
	public int getAccess() {
		return this.access;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	@Override
	public String[] getExceptions() {
		return this.exceptions;
	}


	public void UMLaccept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public void acceptUML(IVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptSequence(IVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
