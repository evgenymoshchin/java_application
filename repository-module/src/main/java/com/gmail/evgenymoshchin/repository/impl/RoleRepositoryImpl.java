package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.FIND_ROLE_BY_NAME_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.ROLE_NAME_VALUE;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public Role findByRoleByName(RoleEnum roleEnum) {
        Query query = entityManager.createQuery(FIND_ROLE_BY_NAME_QUERY);
        query.setParameter(ROLE_NAME_VALUE, roleEnum);
        return (Role) query.getSingleResult();
    }
}
