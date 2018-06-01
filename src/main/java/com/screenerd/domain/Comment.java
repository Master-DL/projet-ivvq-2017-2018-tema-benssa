package com.screenerd.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        if(content != null? !content.equals(comment.content): comment.content!=null) return false;
        if(post != null? !post.equals(comment.post): comment.post!=null) return false;
        return user!=null? user.equals(comment.user): comment.user == null;
    }

    @Override
    public int hashCode() {
        int result = content!=null?content.hashCode():0;
        result = 31 * result + (post!=null? post.hashCode() :0);
        result = 31 * result + (user!=null? user.hashCode() :0);
        return result;
    }
}

