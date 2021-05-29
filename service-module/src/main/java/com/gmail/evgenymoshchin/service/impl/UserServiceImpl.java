package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.MailService;
import com.gmail.evgenymoshchin.service.PasswordService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.converters.UserServiceConverter;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.USER_WAS_NOT_FOUND_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.SUCCESSFUL_MAIL_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.USER_EXIST_EXCEPTION_MESSAGE_VALUE;
import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final MailService mailService;
    private final UserServiceConverter converter;

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        if (emailExist(userDTO.getUsername())) {
            throw new UserAlreadyExistException(USER_EXIST_EXCEPTION_MESSAGE_VALUE
                    + userDTO.getUsername());
        } else {
            userRepository.persist(converter.convertDTOtoUser(userDTO));
        }
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.nonNull(user)) {
            return user;
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, username));
        }
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            userRepository.remove(user);
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public void updateUserRoleById(Long id, RoleEnum roleEnum) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            Role role = roleRepository.findRoleByName(roleEnum);
            user.setRole(role);
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public void updatePasswordById(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
//        String generatedPassword = passwordService.generateRandomPassword();
            String generatedPassword = "1111";
            user.setPassword(passwordEncoder.encode(generatedPassword));
//        mailService.sendEmail(user.getUsername(), generatedPassword);
            log.info(SUCCESSFUL_MAIL_MESSAGE, user.getUsername());
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public UserPageDTO findUsersWithPagination(int pageNumber, int pageSize) {
        UserPageDTO userPage = new UserPageDTO();
        List<User> users = userRepository.findWithPagination(pageNumber, pageSize);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(converter.convertUserToDTO(user));
        }
        userDTOS.sort(Comparator.comparing(UserDTO::getUsername));
        userPage.getUsers().addAll(userDTOS);
        Long countOfUsers = userRepository.getCount();
        userPage.setPagesCount(countOfUsers);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfUsers);
        userPage.getNumbersOfPages().addAll(numbersOfPages);
        return userPage;
    }

    private boolean emailExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
