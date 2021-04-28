package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        userRepository.persist(convertDTOtoUser(userDTO));
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(convertUserToDTO(user));
        }
        return userDTOS;
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        User user = userRepository.findById(id);
        userRepository.remove(user);
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPatronymic((user.getPatronymic()));
        userDTO.setUsername(user.getUsername());
        RoleEnum roleEnum = (user.getRole().getName());
        userDTO.setRole(roleEnum);
        return userDTO;
    }

    private User convertDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setUsername(userDTO.getUsername());
        Role role = roleRepository.findByRoleByName(userDTO.getRole());
        String generatedPassword = generateRandomPassword();
        logger.info(generatedPassword);
        user.setPassword(passwordEncoder.encode(generatedPassword));
        user.setRole(role);
        return user;
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return "user" + random.nextInt(100);
    }
}
