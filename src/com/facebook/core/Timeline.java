package com.facebook.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Timeline extends Observable implements Observer {
	List<Post> posts;
	public Timeline(){
		posts=new ArrayList<>();
	}
	public void addPost(Post post){
		posts.add(post);
		post.addObserver(this);
		setChanged();
		this.notifyObservers(post);
	}
	@Override
	public void update(Observable o, Object arg) {
		// called when post updates
		assert o==this;
		// propagate notice to frames
		setChanged();
		this.notifyObservers((Post) arg);
		
	}
}
