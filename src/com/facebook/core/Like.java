package com.facebook.core;

import java.util.Observable;

import javax.swing.JButton;

import com.facebook.api.IPost;

public class Like extends Observable {
	Person liker;
	IPost beingLiked;
	
	public boolean liked; // liked status is public so Instagram interns can access
	
	public Like(Person person, IPost post, boolean liked){
		liker=person;
		beingLiked=post;
		this.liked=liked;
	}
	public boolean isLiked(){
		return liked;
	}
	public Person getLiker() {
		return liker;
	}
	public IPost getPost() {
		return beingLiked;
	}
	public boolean toggleLiked(JButton button) {
		// FIXME: why does this function need to know about JButton and its possible text?
		liked=!liked;
		button.setText(getButtonText());
		setChanged();
		this.notifyObservers(liked);
		return liked;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Like)){
			return false;
		}
		Like other=(Like)o;
		return this.liker.equals(other.liker) && this.beingLiked.equals(other.beingLiked);
	}
	@Override
	public int hashCode(){
		return liker.hashCode()+beingLiked.hashCode();
	}
	public String getButtonText() {
		String text="";
		if(liked){
			text="Liked";
		}else{
			text="Like this post";
		}
		return text;
		
	}
}
