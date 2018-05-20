package com.screenerd.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private  List<Like> likes = new ArrayList<>();

    public Post(){}

    public Post(User u, byte[] i, String iF, String d) {
        this.user = u;
        this.image = i;
        this.description = d;
        this.imageFormat = iF;
    }

    public void addLike(Like like) {
        likes.add(like);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public User getUser() {
        return this.user;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return this.description;
    }


}
