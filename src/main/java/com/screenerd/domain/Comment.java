package com.screenerd.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    @Size(min=2)
    private String content;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private  Post post;

    public Comment(String content, User user, Post post){
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public Comment(){
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
    
    public Post getPost(){
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

