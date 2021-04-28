package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.User;

public interface UserRepository extends GenericRepository<Long, User> {

    User findByUsername(String username);
}
