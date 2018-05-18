package com.screenerd.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by telly on 09/03/18.
 */

@Entity(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Max(5)
    @Min(1)
    private int value;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Post post;

    public Like(){}

    public Like(int value,User user,Post post){
        this.user = user;
        this.post = post;
        this.value = value;
    }
}
