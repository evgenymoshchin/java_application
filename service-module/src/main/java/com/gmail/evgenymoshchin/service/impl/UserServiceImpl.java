package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.PasswordService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserPageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.MAIL_PASSWORD_CHANGE_NOTIFICATION_VALUE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.MAIL_TEXT_NOTIFICATION_VALUE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.SUCCESSFUL_MAIL_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.USER_EXIST_EXCEPTION_MESSAGE_VALUE;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final JavaMailSender javaMailSender;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordService passwordService,
                           JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) throws UserAlreadyExistException {
        if (emailExist(userDTO.getUsername())) {
            throw new UserAlreadyExistException(USER_EXIST_EXCEPTION_MESSAGE_VALUE
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
    public void removeUserById(Long id) {
        User user = userRepository.findById(id);
        userRepository.remove(user);
    }

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
        String generatedPassword = passwordService.generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));
        sendEmail(user.getUsername(), generatedPassword);
        logger.info(SUCCESSFUL_MAIL_MESSAGE, user.getUsername());
    }

    @Override
    @Transactional
    public UserPageDTO findUsersWithPagination(Integer pageNumber, Integer pageSize) {
        UserPageDTO userPage = new UserPageDTO();
        List<User> users = userRepository.findWithPagination(pageNumber, pageSize);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(convertUserToDTO(user));
        }
        userDTOS.sort(Comparator.comparing(UserDTO::getUsername));
        userPage.getUsers().addAll(userDTOS);
        Long countOfUsers = userRepository.getCount();
        userPage.setPagesCount(countOfUsers);
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, Math.toIntExact(countOfUsers / pageSize + 1))
                .boxed()
                .collect(Collectors.toList());
        userPage.getNumbersOfPages().addAll(numbersOfPages);
        return userPage;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPatronymic((user.getPatronymic()));
        userDTO.setUsername(user.getUsername());
        if (Objects.nonNull(user.getRole())) {
            RoleEnum roleEnum = (user.getRole().getName());
            userDTO.setRole(roleEnum);
        }
        return userDTO;
    }

    private User convertDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setUsername(userDTO.getUsername());
        Role role = roleRepository.findByRoleByName(userDTO.getRole());
        user.setRole(role);
        String generatedPassword = passwordService.generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));
        sendEmail(userDTO.getUsername(), generatedPassword);
        return user;
    }

    private boolean emailExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private void sendEmail(String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject(MAIL_PASSWORD_CHANGE_NOTIFICATION_VALUE);
        message.setText(String.format(MAIL_TEXT_NOTIFICATION_VALUE, username, password));
//        javaMailSender.send(message);
    }
}
