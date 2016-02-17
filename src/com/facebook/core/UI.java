package com.facebook.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI extends JFrame implements Observer{
	
	private static final long serialVersionUID = -353503589500393441L;
	Person owner;
	Timeline posts;
	HashMap<Post, JLabel> postToLabel;
	HashMap<Post, JButton> postToLike;
	List<JLabel> labels;
	JPanel panel;
	
	public UI(Person p, Timeline posts){
		this.owner=p;
		this.posts=posts;
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		labels=new ArrayList<>();
		postToLabel=new HashMap<>();
		postToLike=new HashMap<>();

		posts.addObserver(this);
		for(Post post : posts.posts){
			showPost(post);
		}
		this.getContentPane().add(panel);
		this.setTitle(p.name);
		this.pack();
	}
	
	public void showPost(final Post post){
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		final JLabel label=new JLabel(post.getText());
		final JButton likeButton=new JButton("Like");
		likeButton.setText(post.getLike(owner).getButtonText());
		likeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				post.getLike(UI.this.owner).toggleLiked(likeButton);
				label.setText(post.getText());
				post.notifyObservers(post);

				UI.this.pack();
			}
		});
		labels.add(label);
		postToLabel.put(post, label);
		postToLike.put(post, likeButton);
		panel.add(label);
		panel.add(likeButton);
		this.panel.add(panel);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// post updated
		Post post = (Post) arg;
		
		// update post text
		JLabel label = postToLabel.get(post);
		label.setText(post.getText());
		
		// update button text
		JButton likeButton = postToLike.get(post);
		likeButton.setText(post.getLike(owner).getButtonText());
	}
}
