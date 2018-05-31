package com.screenerd.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    private String description;

    @OneToMany
    @JsonIgnore
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

    public void removeLike(Like like){
        likes.remove(like);
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public User getUser() {
        return this.user;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getId() {
        return this.id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
