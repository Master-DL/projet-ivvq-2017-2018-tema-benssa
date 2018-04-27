package com.screenerd.repository;

import com.screenerd.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mathieukostiuk on 27/04/2018.
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
