package com.screenerd.repository;

import com.screenerd.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by telly on 17/05/18.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
   void deleteById(Long id);
}
