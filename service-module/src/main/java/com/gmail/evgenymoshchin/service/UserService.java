package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserPageDTO;

public interface UserService {
    void addUser(UserDTO userDTO);

    User findUserByUsername(String username);

    void removeUserById(Long id);

    void updateUserRoleById(Long id, RoleEnum roleEnum);

    void updatePasswordById(Long id);

    UserPageDTO findUsersWithPagination(Integer pageNumber, Integer pageSize);
}
