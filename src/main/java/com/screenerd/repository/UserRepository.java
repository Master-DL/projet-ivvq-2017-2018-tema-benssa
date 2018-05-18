package com.screenerd.repository;

import com.screenerd.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by telly on 17/05/18.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
