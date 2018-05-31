package com.screenerd.service

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.CommentRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

class CommentServiceSpec extends Specification {

    CommentService commentService
    CommentRepository commentRepository

    void setup() {
        commentRepository = Mock()
        commentService = new CommentService()
        commentService.commentRepository = commentRepository
    }

    def "check the type of the repository"() {
        expect: "the repository is a Crud Repository"
        commentRepository instanceof CrudRepository
    }

    def "test save of a Comment in the repository"() {
        given: "a comment"
        def comment = Mock(Comment) {

            getUser() >> Mock(User){
                getComments() >> new ArrayList<Comment>()
            }
            getPost() >> Mock(Post)
            getContent() >> ""
        }

        when: "the comment is saved"
        commentService.saveComment(comment)

        then: "the save is delegated to the repository"
        1 * commentRepository.save(comment)
    }

    def "test delegation of delete a comment to commentRepository"(){
        given: "an existing comment with id 1"
        commentRepository.findOne(1) >> Mock(Comment){
            getUser() >> Mock(User){
                getComments() >> []
            }
            getPost() >> Mock(Post){
                getComments() >> []
            }
        }

        when: "the like is deleted"
        commentService.deleteComment(1)

        then: "the delete is delegated to the likeRepository"
        1 * commentRepository.delete(1)
    }
}
