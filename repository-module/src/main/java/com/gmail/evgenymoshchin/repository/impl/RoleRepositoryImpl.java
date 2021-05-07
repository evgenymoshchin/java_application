package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public Role findByRoleByName(RoleEnum roleEnum) {
        Query query = entityManager.createQuery("from Role as r where r.name=:name");
        query.setParameter("name", roleEnum);
        return (Role) query.getSingleResult();
    }
}
