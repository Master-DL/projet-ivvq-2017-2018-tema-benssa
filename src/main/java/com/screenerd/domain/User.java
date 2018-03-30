package com.screenerd.domain;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;


    private String login;
    private String password;
    private byte[] avatar;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Like> likes;

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


}
