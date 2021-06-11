package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.FIND_USER_WITH_ROLE_BY_USERNAME_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.USERNAME_VALUE;

@Log4j2
@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery(FIND_USER_WITH_ROLE_BY_USERNAME_QUERY);
        query.setParameter(USERNAME_VALUE, username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}

