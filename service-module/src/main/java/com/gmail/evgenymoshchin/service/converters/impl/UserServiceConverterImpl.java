package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.MailService;
import com.gmail.evgenymoshchin.service.PasswordService;
import com.gmail.evgenymoshchin.service.converters.UserServiceConverter;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserServiceConverterImpl implements UserServiceConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final MailService mailService;

    public UserServiceConverterImpl(PasswordEncoder passwordEncoder,
                                    RoleRepository roleRepository,
                                    PasswordService passwordService,
                                    MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.mailService = mailService;
    }

    @Override
    public UserDTO convertUserToDTO(User user) {
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

    @Override
    public User convertDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setUsername(userDTO.getUsername());
        if (Objects.nonNull(userDTO.getRole())){
            Role role = roleRepository.findByRoleByName(userDTO.getRole());
            user.setRole(role);
        }
        String generatedPassword = passwordService.generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));
        mailService.sendEmail(userDTO.getUsername(), generatedPassword);
        return user;
    }
}
