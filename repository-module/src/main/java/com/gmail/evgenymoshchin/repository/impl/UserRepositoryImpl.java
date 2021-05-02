package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.FIND_USER_WITH_ROLE_BY_USERNAME_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.USERNAME_VALUE;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery(FIND_USER_WITH_ROLE_BY_USERNAME_QUERY);
        query.setParameter(USERNAME_VALUE, username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

