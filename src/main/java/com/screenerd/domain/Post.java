package com.screenerd.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    private byte[] image;

    @NotNull
    private String imageFormat;

    @ManyToOne
    @NotNull
    private User user;

    @NotNull
    private String description;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private  List<Like> likes;


    public Post(){}
}
