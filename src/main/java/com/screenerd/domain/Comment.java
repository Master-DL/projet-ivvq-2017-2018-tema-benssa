package com.screenerd.domain;

import javax.persistence.*;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private  Post post;

    public Comment(){}
}
