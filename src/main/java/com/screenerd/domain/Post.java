package com.screenerd.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
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
    @NotFound(
            action = NotFoundAction.IGNORE)
    private  List<Like> likes = new ArrayList<>();

    @NotNull
    @Min(1)
    @Max(5)
    public Integer popularity;

    public Post(){}

    public Post(User u, byte[] i, String iF, String d) {
        this.user = u;
        this.image = i;
        this.description = d;
        this.imageFormat = iF;
        this.popularity = 1;
    }

    public void addLike(Like like) {
        likes.add(like);
        refreshPopularity();
    }

    public void removeLike(Like like){
        System.out.println(likes.remove(like));
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment){comments.remove(comment);}
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

    public Integer getPopularity() {
        return popularity;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        if(image != null? !Arrays.equals(image,post.image): post.image!=null) return false;
        if(imageFormat != null? !imageFormat.equals(post.imageFormat): post.imageFormat!=null) return false;
        if(description != null? !description.equals(post.description): post.description!=null) return false;
        return user != null? user.equals(post.user): post.user == null;
    }

    @Override
    public int hashCode() {
        int result = image!=null? Arrays.hashCode(image): 0;
        result = 31 * result + (imageFormat!=null? imageFormat.hashCode(): 0);
        result = 31 * result + (description!=null? description.hashCode(): 0);
        result = 31 * result + (user!=null? user.hashCode(): 0);
        return result;
    }

    @Override
    public String toString() {
        return id + " " + description +" " + Arrays.hashCode(image) + " " +imageFormat+" "+user;
    }

    private void refreshPopularity() {
        int sum = 0;
        for (Like l : this.likes)
            sum = sum + l.getValue();
        this.popularity = sum/this.likes.size();
        this.popularity = sum/this.likes.size();
    }
}
