package com.screenerd.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by telly on 09/03/18.
 */

@Entity(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Max(5)
    @Min(1)
    private int value;

    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public Like(){}

    public Like(int value,User user,Post post){
        this.user = user;
        this.post = post;
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass()!= o.getClass()) return false;
        Like like = (Like) o;
        if(value!=like.value) return false;
        if(post != null? !post.equals(like.post): post!=null) return false;
        return user!=null? user.equals(like.user): like.user == null;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (post != null? post.hashCode(): 0);
        result = 31 * result + (user != null? user.hashCode(): 0);
        return result;
    }
}
