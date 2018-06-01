package com.screenerd.repository;

import com.screenerd.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Benjamin on 13/04/2018.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}