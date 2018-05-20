package com.screenerd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotEmpty
    private String login;

    @NotNull @Size(min = 6)
    private String password;

    @NotNull
    private byte[] avatar;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    public User(){}

    public byte[] getAvatar() {
        return avatar;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**public void addComment(Comment comment) {
        comments.add(comment);
    }
    public void addLike(Comment comment) {
        comments.add(comment);
    }**/

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User(String login, String password, byte[] avatar) {
        this.login = login;
        this.password = password;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void addLike(Like like){
        likes.add(like);
    }
}
