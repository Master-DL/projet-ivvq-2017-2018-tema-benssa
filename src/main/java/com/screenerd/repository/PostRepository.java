package com.screenerd.repository;

import com.screenerd.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by telly on 17/05/18.
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post,Long> {
    Page<Post> findAllByOrderByPopularityDesc(Pageable pageable);
}



