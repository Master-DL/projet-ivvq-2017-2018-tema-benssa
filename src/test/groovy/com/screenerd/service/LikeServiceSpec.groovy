package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification


/**
 * Created by telly on 18/05/18.
 */

class LikeServiceSpec extends Specification{

    LikeRepository likeRepository
    LikeService likeService


    def setup(){
        likeRepository = Mock() {}
        likeService = new LikeService()
        likeService.postRepository = Mock(PostRepository)
        likeService.likeRepository = likeRepository
    }

    def "check type of likeRepositry"(){
        expect: "likeRepository is a crudRepository"
        likeRepository instanceof CrudRepository
    }

    def "test delegation of save of a like to likeRepository"(){
        given: "a like"
        Like like = Mock(Like){
            getUser() >> Mock(User){
                getId() >> 1
            }
            getPost() >> Mock(Post){
                getId() >> 1
            }
        }

        when: "the like is saved"
        likeService.saveLike(like)

        then: "the save is delegated to the likeRepository"
        1 * likeRepository.save(like)
    }

    def "test delegation of delete a like to likeRepository"(){
        given: "an existing like with id 1"
        likeRepository.findOne(1) >> Mock(Like){
            getUser() >> Mock(User)
            getPost() >> Mock(Post)
        }

        when: "the like is deleted"
        likeService.deleteLike(1)

        then: "the delete is delegated to the likeRepository"
        1 * likeRepository.delete(1)
    }
}
