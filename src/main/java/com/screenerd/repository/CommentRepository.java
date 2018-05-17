package com.screenerd.repository;

import com.screenerd.domain.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by telly on 17/05/18.
 */
public interface CommentRepository extends CrudRepository<Comment,Long> {
}
