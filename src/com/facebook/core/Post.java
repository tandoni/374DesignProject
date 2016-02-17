package com.facebook.core;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import com.facebook.api.IPost;

public class Post extends Observable implements IPost, Observer{
	Person author;
	Person owner;
	String richText;
	HashMap<Person,Like> likes;  
	public Post(Person author, Person owner, String richText){
		likes=new HashMap<Person, Like>();
		this.author=author;
		this.owner=owner;
		this.richText=richText;
	}
	@Override
	public Person getAuthor() {
		return author;
	}
	@Override
	public Person getOwner() {
		return owner;
	}
	@Override
	public int getLikes() {
		int count=0;
		for(Like l: likes.values()){
			if(l.liked){
				count++;
			}
		}
		return count;
	}
	@Override
	public void setLike(Person liker, boolean liked) {
		if(!likes.containsKey(liker)){
			Like like=new Like(liker, this, liked);
			like.addObserver(this);
			likes.put(liker,like);
		}

		likes.get(liker).liked=liked;
		setChanged();
		this.notifyObservers(this);
	}//	private void updateText() {
//		setText("<html>"+author+" wrote on your timeline: <br>"+richText+"<br>"+getLikes()+" likes</html>");
//	}
	@Override
	public String getText() {
		return "<html>"+author+" wrote on "+owner+"'s timeline: <br>"+richText+"<br>"+getLikes()+" likes</html>";
	}
	@Override
	public void update(Observable o, Object arg) {
		// like updated -> redraw
		setChanged();
		this.notifyObservers(this);
	}
	public Like getLike(Person liker) {
		if(!likes.containsKey(liker)){
			Like like=new Like(liker, this, false);
			like.addObserver(this);
			likes.put(liker,like);
		}
		
		return likes.get(liker);
	}
}
