package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.UserDTO;

import java.util.List;

public interface UserService {
    void addUser(UserDTO userDTO);

    User findUserByUsername(String username);

    List<UserDTO> findAll();

    void removeUserById(Long id);
}
