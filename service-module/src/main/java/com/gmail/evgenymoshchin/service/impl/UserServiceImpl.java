package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private static final Random RANDOM = new Random();

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository, JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) throws UserAlreadyExistException {
        if (emailExist(userDTO.getUsername())) {
            throw new UserAlreadyExistException("There is an user with that email: "
                    + userDTO.getUsername());
        } else {
            userRepository.persist(convertDTOtoUser(userDTO));
        }
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

//    @Override
//    @Transactional
//    public UserDTO findUserById(Long id) {
//        User user = userRepository.findById(id);
//        return convertUserToDTO(user);
//    }

    @Override
    @Transactional
    public void updateUserRoleById(Long id, RoleEnum roleEnum) {
        User user = userRepository.findById(id);
        Role role = roleRepository.findByRoleByName(roleEnum);
        user.setRole(role);
    }

    @Override
    @Transactional
    public void updatePasswordById(Long id) {
        User user = userRepository.findById(id);
        String generatedPassword = generateRandomPassword();
        logger.info("New password for user {} is {}", user.getUsername(), generatedPassword);
        user.setPassword(passwordEncoder.encode(generatedPassword));
    }

    private boolean emailExist(String username) {
        return userRepository.findByUsername(username) != null;
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
        logger.info("Password for user {} is {}", user.getUsername(), generatedPassword);
        sendSimpleEmail(userDTO.getUsername(), generatedPassword);
        user.setPassword(passwordEncoder.encode(generatedPassword));
        user.setRole(role);
        return user;
    }

    private String generateRandomPassword() {
        return "user" + RANDOM.nextInt(100);
    }

    private void sendSimpleEmail(String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject("Password for user " + username + " is " + password);
        message.setText(password);
        javaMailSender.send(message);
    }
}
