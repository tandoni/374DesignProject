package com.facebook;

import javax.swing.JFrame;

import com.facebook.core.UI;
import com.facebook.core.Person;
import com.facebook.core.Post;
import com.facebook.core.Timeline;

public class Client {
	
	public static void main(String[] args){
		Person alice=new Person("Alice");
		Person bob=new Person("Bob");
		Person carol=new Person("Carol");
		
		Post post=new Post(bob, alice, "Happy Birthday Alice!");
		
		Timeline alicedb=new Timeline();
		alicedb.addPost(post);
		UI aliceClient = new UI(alice, alicedb);
		
		Timeline bobdb=new Timeline();
		bobdb.addPost(post);
		UI bobClient = new UI(bob, bobdb);
		bobClient.setLocation(200,200);

		// Alice likes Bob's post; thanks for the birthday wishes!
		post.setLike(alice, true);
		
		// Alice's mom, Carol, always likes everything on Alice's page
		post.setLike(carol, true);
		
		aliceClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aliceClient.setVisible(true);
		
		bobClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bobClient.setVisible(true);
	}
}
