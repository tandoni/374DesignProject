package com.facebook.api;

import com.facebook.core.Person;

/***
 * 
 * DO NOT CHANGE THIS INTERFACE
 * 
 */
public interface IPost {
	String getText();
	Person getAuthor();
	Person getOwner();
	void setLike(Person liker, boolean liked);
	int getLikes();
}
