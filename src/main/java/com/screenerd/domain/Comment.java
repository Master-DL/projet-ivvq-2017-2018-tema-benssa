package com.screenerd.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by telly on 09/03/18.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    @Size(min=2)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private  Post post;

    public Comment(){}
}
