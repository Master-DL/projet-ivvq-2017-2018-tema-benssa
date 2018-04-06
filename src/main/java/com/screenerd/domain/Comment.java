package com.screenerd.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private  Post post;

    public Comment(String content, User user, Post post){
        this.content = content;
        this.user = user;
        this.post = post;
    }
    
    public Long getId(){
    	return id;
    }
    
    public String getContent(){
    	return content;
    }
    
    public User getUser(){
    	return user;
    }
    
    public User getPost(){
    	return post;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
    
    public void setContent(String content){
    	this.content = content;
    }
    
    public void setPost(Post post){
    	this.post = post;
    }
    
}

