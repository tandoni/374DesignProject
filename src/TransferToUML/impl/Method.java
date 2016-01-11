package TransferToUML.impl;

import TransferToUML.api.IMethod;
import TransferToUML.visitor.ITraverser;
import TransferToUML.visitor.IVisitor;

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


	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

}
