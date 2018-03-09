package com.screenerd.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Comment {

    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private  Post post;

    public Comment(){}
}
