package TransferToUML.impl;

import java.util.ArrayList;
import java.util.Collection;

import TransferToUML.api.ISequence;

public class Sequence implements ISequence {
	private String fromClass = "";
	private String toClass = "";
	private String calledMethod = "";
	private ArrayList<String> arguments = new ArrayList<String>();

	public Sequence() {

	}

	public Sequence(String from, String to, String method, String[] args) {
		this.fromClass = from;
		this.toClass = to;
		this.calledMethod = method;
		this.addArguments(args);
	}

	public void addFromClass(String fromClass) {
		this.fromClass = fromClass;
	}

	public void addToClass(String toClass) {
		this.toClass = toClass;
	}

	public void addCalledMethod(String calledMethod) {
		this.calledMethod = calledMethod;
	}

	public void addArguments(String[] arguments) {
		for (String arg : arguments) {
			this.arguments.add(arg);
		}
	}

	public String getFromClass() {
		return this.fromClass;
	}

	public String getToClass() {
		return this.toClass;
	}

	public String getCalledMethod() {
		return this.calledMethod;
	}

	public ArrayList<String> getArguments() {
		return this.arguments;
	}

	public void setCalledMethod(String string) {
		this.calledMethod = string;
	}
}