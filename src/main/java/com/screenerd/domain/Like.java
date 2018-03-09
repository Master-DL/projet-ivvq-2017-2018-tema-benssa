package com.screenerd.domain;

import javax.persistence.*;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private int value;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Like(){}
}
