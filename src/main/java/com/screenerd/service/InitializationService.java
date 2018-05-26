package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by telly on 21/05/18.
 */
@Service
@Transactional
public class InitializationService {

    private User thomas;
    private User sarah;
    private User ben;

    private Post fortniteByThomas;
    private Post pesByThomas;
    private Post catBySarah;

    private Comment benOnFortnite;
    private Comment thomasOnCat;
    private Comment benOnCat;

    private Like sarahLovesFortnite;
    private Like benHatesFortnite;
    private Like benHatesCat;
    private Like benLovesPes;

    private void initThomas(){
        thomas = new User("thomas","password",new byte[]{1,2,3});
    }

    private void initSarah(){
        sarah = new User("sarah","HarS12a0",new byte[]{2,5,6,7});
    }

    private void initBen() {
        ben = new User("benji", "rienACirer", new byte[]{2, 5, 6, 7});
    }

    public void initPosts(){
        initThomas();
        fortniteByThomas = new Post(thomas,new byte[]{1,2,3},"png","mate ton top1 sur fortnite");
        pesByThomas = new Post(thomas,new byte[]{1,0,6,8},"png","controle ,frappe enchainee");
        initSarah();
        catBySarah = new Post(sarah,new byte[]{1,2,9,5,7},"png","il est mimi mon chaton");
    }

    public void initComments(){
        initBen();
        benOnFortnite = new Comment("un jeu de barbare",ben,fortniteByThomas);
        thomasOnCat = new Comment("on en veut pas de ton chat ici.Gamer Only",thomas,catBySarah);
        benOnCat = new Comment("trop relou ces chats",ben,catBySarah);
    }

    public void initLikes(){
        sarahLovesFortnite = new Like(5,sarah,fortniteByThomas);
        benHatesFortnite = new Like(1,ben,fortniteByThomas);
        benHatesCat = new Like(1,ben,catBySarah);
        benLovesPes = new Like(4,ben,pesByThomas);
    }

    public User getThomas() {
        return thomas;
    }

    public User getSarah() {
        return sarah;
    }

    public User getBen() {
        return ben;
    }

    public Post getFortniteByThomas() {
        return fortniteByThomas;
    }

    public Post getCatBySarah() {
        return catBySarah;
    }

    public Post getPesByThomas() {
        return pesByThomas;
    }

    public Comment getBenOnFortnite() {
        return benOnFortnite;
    }

    public Comment getBenOnCat() {
        return benOnCat;
    }

    public Comment getThomasOnCat() {
        return thomasOnCat;
    }

    public Like getBenHatesFortnite() {
        return benHatesFortnite;
    }

    public Like getBenHatesCat() {
        return benHatesCat;
    }

    public Like getSarahLovesFortnite() {
        return sarahLovesFortnite;
    }

    public Like getBenLovesPes() {
        return benLovesPes;
    }
}
