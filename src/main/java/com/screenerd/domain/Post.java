package com.screenerd.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private byte[] image;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private  List<Like> likes;


    public Post(){}
}
