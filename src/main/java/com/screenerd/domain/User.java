package com.screenerd.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class User {

    private String login;
    private String password;
    private byte[] avatar;

    @OneToMany
    private List<Comment> myComments;

    @OneToMany
    private List<Post> myPosts;

    @OneToMany
    private List<Like> myLikes;

    public User(){}
}
