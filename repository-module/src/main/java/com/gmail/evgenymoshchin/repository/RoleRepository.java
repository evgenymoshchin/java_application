package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;

public interface RoleRepository extends GenericRepository<Long, Role> {
    Role findRoleByName(RoleEnum roleEnum);
}
