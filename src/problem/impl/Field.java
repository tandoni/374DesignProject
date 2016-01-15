package problem.impl;

import problem.interfaces.IField;
import problem.interfaces.ISequence;
import problem.visitor.IVisitor;

public class Field implements IField {
	private int access;
	private String name;
	private String description;
	private String signature;
	private Object value;

	public Field(int access, String name, String desc, String signature, Object value) {
		this.access = access;
		this.name = name;
		this.description = desc;
		this.signature = signature;
		this.value = value;
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
	public Object getValue() {
		return this.value;
	}

	@Override
	public void acceptUML(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

//	@Override
//	public void acceptSequence(IVisitor v) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void acceptSequence(IVisitor v, ISequence subMethods, int depth) {
		// TODO Auto-generated method stub
		System.out.println("Field : acceptSequence");
	}

}
