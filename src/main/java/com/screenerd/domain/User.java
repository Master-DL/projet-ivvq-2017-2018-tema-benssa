package com.screenerd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    @Size(min = 1)
    private String login;

    @NotNull @Size(min = 6)
    private String password;

    @NotNull
    private byte[] avatar;

    @OneToMany
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    public User(){}

    public User(String login, String password, byte[] avatar) {
        this.login = login;
        this.password = password;
        this.avatar = avatar;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Post> getPosts() {
        return posts;
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

    public void removeLike(Like like){
        likes.remove(like);
    }
    public void addPost(Post post){
        posts.add(post);
    }

    public void removePost(Post post){
        posts.remove(post);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if(login!=null? !login.equals(user.login): user.login!=null) return false;
        if(password!=null? !password.equals(user.password): user.password!=null) return false;
        return avatar!=null? Arrays.equals(avatar,user.avatar): user.avatar==null;
    }

    @Override
    public int hashCode() {
        int result = login!=null?login.hashCode():0;
        result = 31 * result + (password!=null? password.hashCode():0);
        result = 31 * result + (avatar!=null? Arrays.hashCode(avatar):0);
        return result;
    }

}
