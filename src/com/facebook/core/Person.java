package com.facebook.core;

import java.util.ArrayList;
import java.util.List;

public class Person {
	public String name;
	String user_id;
	String email;
	List<Person> closeFriends;
	public Person(String name){
		this.name=name;
		user_id=name;
		email=name;
		closeFriends=new ArrayList<>();
	}
	public String toString(){
		return name;
	}
}
