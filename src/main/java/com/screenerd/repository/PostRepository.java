package com.screenerd.repository;

import com.screenerd.domain.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by telly on 17/05/18.
 */
public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

}



