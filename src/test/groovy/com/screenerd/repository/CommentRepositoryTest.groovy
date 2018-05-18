package com.screenerd.service

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import spock.lang.Specification


@DataJpaTest
public class CommentRepositoryTest extends Specification{


    @Autowired
    private CommentRepository socialMediaSiteRepository;

    def comment = new Comment("un content", new User(), new Post())


    def "find comment by Id" () {

        def savedFaceBookEntity  = socialMediaSiteRepository.save(facebook)

        when: "load facebook entity"
        def faceBookEntityFromDb = socialMediaSiteRepository.findOne(savedFaceBookEntity.getId())

        then:"saved and retrieved entity by id must be equal"
        savedFaceBookEntity.getId() == faceBookEntityFromDb.getId()
    }
}