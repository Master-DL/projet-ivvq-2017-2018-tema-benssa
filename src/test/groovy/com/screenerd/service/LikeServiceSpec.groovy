package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification


/**
 * Created by telly on 18/05/18.
 */

class LikeServiceSpec extends Specification{

    LikeRepository likeRepository
    LikeService likeService


    def setup(){
        likeRepository = Mock()
        likeService = new LikeService()
        likeService.likeRepository = likeRepository
    }

    def "check type of likeRepositry"(){
        expect: "likeRepository is a crudRepository"
        likeRepository instanceof CrudRepository
    }

    def "delegation of save of a like to likeRepository"(){
        given: "a like"
        Like like = Mock(Like){
            getUser() >> Mock(User)
            getPost() >> Mock(Post){
                getUser() >> Mock(User){
                    getPosts() >> []
                }
            }
        }

        when: "the like is saved"
        likeService.saveLike(like)

        then: "the save is delegated to the likeRepository"
        1 * likeRepository.save(like)
    }
}
