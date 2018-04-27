package com.screenerd.repository;

import com.screenerd.domain.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by telly on 13/04/18.
 */
@Repository
public interface LIkeRepository extends CrudRepository<Like,Long>{
}
