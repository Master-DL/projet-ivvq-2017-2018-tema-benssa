package com.screenerd.repository;

import com.screenerd.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by mousa on 13/04/2018.
 */

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    }
