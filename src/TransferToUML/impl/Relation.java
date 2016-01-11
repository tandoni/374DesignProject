package TransferToUML.impl;

import java.util.ArrayList;
import java.util.Collection;

import TransferToUML.api.IRelation;
import TransferToUML.app.TransferToUMLApp;
import TransferToUML.visitor.IVisitor;

public class Relation implements IRelation {
	
	private String subClass;
	private String superClass;
	private Collection<String> interfaces;
	private Collection<String> uses;
	private Collection<String> associations;
	
	public Collection<String> classNames = new ArrayList<String>();
	
	public Relation(String subName) {
		this.subClass = subName;
		this.superClass = "";
		this.interfaces = new ArrayList<String>();
		this.uses = new ArrayList<String>();
		this.associations = new ArrayList<String>();
		setClassNames();
	}
	
	public Relation(String subName, String superClass) {
		this.subClass = subName;
		setClassNames();
		//System.out.println(superClass + " " + this.classNames.contains(getPureName(superClass)) + " " + getPureName(superClass));
		if (this.classNames.contains(getPureName(superClass)))
			this.superClass = superClass;
		this.interfaces = new ArrayList<String>();
		this.uses = new ArrayList<String>();
		this.associations = new ArrayList<String>();
	}
	
	public Relation(String subName, String[] interfaces){
		this.subClass = subName;
		this.superClass = "";
		this.interfaces = new ArrayList<String>();
		setClassNames();
		for(String s : interfaces){
			if (this.classNames.contains(getPureName(s)))
				this.interfaces.add(s);
		}
		this.uses = new ArrayList<String>();
		this.associations = new ArrayList<String>();
	}
	
	public Relation(String subName, String superClass, String[] interfaces) {
		this.subClass = subName;
		setClassNames();
		//System.out.println(superClass + " " + this.classNames.contains(getPureName(superClass))+ " |" + getPureName(superClass)+"|");
		if (this.classNames.contains(getPureName(superClass)))
			this.superClass = superClass;
		this.interfaces = new ArrayList<String>();
		for(String s : interfaces){
			if (this.classNames.contains(getPureName(s)))
				this.interfaces.add(s);
		}
		this.uses = new ArrayList<String>();
		this.associations = new ArrayList<String>();
	}
	
	public String getPureName(String s){
		String[] split = s.split("/");
		return split[split.length-1];
	}
	
	public void setClassNames(){
		for (String s : TransferToUMLApp.classes) {
			String[] split = s.split("\\.");
			s = split[split.length-1];
			//System.out.println("set |" + s + "|");
			this.classNames.add(s);
		}
	}
	
	@Override
	public String getSubClass() {
		return this.subClass;
	}
	
	@Override
	public String getSuperClass() {
		return this.superClass;
	}

	@Override
	public Collection<String> getInterfaces() {
		return this.interfaces;
	}



	public void accept(IVisitor v) {
//		v.preVisit(this);
//		for(IClass p: this.components) {
//			ITraverser t = (ITraverser)p;
//			t.accept(v);
//		}
//		v.postVisit(this);
	}

	@Override
	public Collection<String> getUses() {
		return this.uses;
	}

	@Override
	public Collection<String> getAssociations() {
		return this.associations;
	}

//	@Override
//	public void addSuperClass(String superClass) {
//		
//	}

	@Override
	public void addInterfaces(String[] i) {

		for(String s : interfaces){
			if (!this.interfaces.contains(s) && this.classNames.contains(getPureName(s)))
				this.interfaces.add(s);
		}
	}

	@Override
	public void addUses(String[] useName) {
		for(String s : useName){
			if (!this.uses.contains(s) && this.classNames.contains(getPureName(s)))
				this.uses.add(s);
		}
	}

	@Override
	public void addUses(String s) {
		if (!this.uses.contains(s) && this.classNames.contains(getPureName(s)))
			this.uses.add(s);
	}

	@Override
	public void addAssociations(String[] associationsName) {
		for(String s : associationsName){
			if (!this.associations.contains(s) && this.classNames.contains(getPureName(s)))
				this.associations.add(s);
		}
	}

	@Override
	public void addAssociations(String s) {
		if (!this.associations.contains(s) && this.classNames.contains(getPureName(s)))
			this.associations.add(s);
	}

}
