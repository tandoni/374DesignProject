package TransferToUML.impl;

import TransferToUML.api.IField;
import TransferToUML.visitor.ITraverser;
import TransferToUML.visitor.IVisitor;

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

	@Override
	public void acceptSequence(IVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
