package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.CommentRepository;
import com.screenerd.repository.LikeRepository;
import com.screenerd.repository.PostRepository;
import com.screenerd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by telly on 21/05/18.
 */
@Service
@Transactional
public class InitializationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;

    private User thomas;
    private User sarah;
    private User ben;
    private User mark;

    private Post fortniteByThomas;
    private Post pesByThomas;
    private Post catBySarah;
    private Post fifaByBen;

    private Comment benOnFortnite;
    private Comment thomasOnCat;
    private Comment benOnCat;

    private Like sarahLovesFortnite;
    private Like benHatesFortnite;
    private Like benHatesCat;
    private Like benLovesPes;

    public void initUsers(){
        thomas = new User("thomas","password",new byte[]{1,2,3});
        userRepository.save(thomas);
        sarah = new User("sarah","HarS12a0",new byte[]{2,5,6,7});
        userRepository.save(sarah);
        ben = new User("benji", "rienACirer", new byte[]{2, 5, 6, 7});
        userRepository.save(ben);
        mark = new User("markivert","passwOrdl",new byte[]{2, 5, 6, 7});
        userRepository.save(mark);
    }


    public void initPosts(){
        fortniteByThomas = new Post(thomas,new byte[]{1,2,3},"png","mate ton top1 sur fortnite");
        postRepository.save(fortniteByThomas);
        pesByThomas = new Post(thomas,new byte[]{1,0,6,8},"png","controle ,frappe enchainee");
        postRepository.save(pesByThomas);
        catBySarah = new Post(sarah,new byte[]{1,2,9,5,7},"png","il est mimi mon chaton");
        postRepository.save(catBySarah);
        fifaByBen = new Post(ben,new byte[]{1,2,9,5,7},"png","Messi est demoniaque");
        postRepository.save(fifaByBen);
    }

    public void initComments(){
        benOnFortnite = new Comment("un jeu de barbare",ben,fortniteByThomas);
        commentRepository.save(benOnFortnite);
        thomasOnCat = new Comment("on en veut pas de ton chat ici.Gamer Only",thomas,catBySarah);
        commentRepository.save(thomasOnCat);
        benOnCat = new Comment("trop relou ces chats",ben,catBySarah);
        commentRepository.save(benOnCat);
    }

    public void initLikes(){
        sarahLovesFortnite = new Like(5,sarah,fortniteByThomas);
        likeRepository.save(sarahLovesFortnite);
        benHatesFortnite = new Like(1,ben,fortniteByThomas);
        likeRepository.save(benHatesFortnite);
        benHatesCat = new Like(1,ben,catBySarah);
        likeRepository.save(benHatesCat);
        benLovesPes = new Like(4,ben,pesByThomas);
        likeRepository.save(benLovesPes);
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

    public User getMark() {
        return mark;
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

    public Post getFifaByBen(){return fifaByBen;}
}
