package com.screenerd.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Post {

    private byte[] image;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private  List<Like> likes;


    public Post(){}
}
