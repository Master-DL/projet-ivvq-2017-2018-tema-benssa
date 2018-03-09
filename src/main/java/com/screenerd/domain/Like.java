package com.screenerd.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Like {

    private int value;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Like(){}
}
