package problem.impl;

import java.util.ArrayList;

import problem.interfaces.IMethod;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public class Method implements IMethod {
	private int access;
	private String name;
	private String description;
	private String signature;
	//private String[] exceptions;
	private ArrayList<ISequence> subMethods;

	public Method(int access, String name, String desc, String signature, ArrayList<ISequence> subMethod) {
		this.access = access;
		this.name = name;
		this.description = desc;
		this.signature = signature;
		this.subMethods = subMethod;
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

//	@Override
//	public String[] getExceptions() {
//		return this.exceptions;
//	}
	
	@Override
	public ArrayList<ISequence> getSubMethods() {
		return subMethods;
	}

	@Override
	public void acceptUML(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public void acceptSequence(IVisitor v, int depth) {
		System.out.println("Method : acceptSequence");
		v.visit(this);
	}

}
