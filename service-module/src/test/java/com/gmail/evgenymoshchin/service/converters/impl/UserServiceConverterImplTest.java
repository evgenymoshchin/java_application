package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.MailService;
import com.gmail.evgenymoshchin.service.PasswordService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceConverterImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordService passwordService;
    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceConverterImpl userServiceConverter;

    @Test
    void shouldConvertUserToDTOAndReturnCorrectId() {
        User user = new User();
        Long testId = 1L;
        user.setId(testId);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(testId, userDTO.getId());
    }

    @Test
    void shouldConvertUserToDTOAndReturnCorrectFirstName() {
        User user = new User();
        String testFirstName = "ivan";
        user.setFirstName(testFirstName);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(testFirstName, userDTO.getFirstName());
    }

    @Test
    void shouldConvertUserToDTOAndReturnCorrectLastName() {
        User user = new User();
        String testLastName = "ivanov";
        user.setLastName(testLastName);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(testLastName, userDTO.getLastName());
    }

    @Test
    void shouldConvertUserToDTOAndReturnCorrectPatronymic() {
        User user = new User();
        String testPatronymic = "ivanych";
        user.setPatronymic(testPatronymic);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(testPatronymic, userDTO.getPatronymic());
    }

    @Test
    void shouldConvertUserToDTOAndReturnCorrectUsername() {
        User user = new User();
        String testUsername = "ivan@gmail.com";
        user.setUsername(testUsername);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(testUsername, userDTO.getUsername());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRole() {
        User user = new User();
        Role role = new Role();
        role.setName(RoleEnum.ROLE_SALE_USER);
        user.setRole(role);
        UserDTO userDTO = userServiceConverter.convertUserToDTO(user);
        Assertions.assertEquals(role.getName(), userDTO.getRole());
    }

    @Test
    void shouldConvertDTOToUserAndReturnCorrectFirstName() {
        UserDTO userDTO = new UserDTO();
        String testFirstName = "ivan";
        userDTO.setFirstName(testFirstName);
        User user = userServiceConverter.convertDTOtoUser(userDTO);
        Assertions.assertEquals(testFirstName, user.getFirstName());
    }

    @Test
    void shouldConvertDTOToUserAndReturnCorrectLastName() {
        UserDTO userDTO = new UserDTO();
        String testLastName = "ivanov";
        userDTO.setLastName(testLastName);
        User user = userServiceConverter.convertDTOtoUser(userDTO);
        Assertions.assertEquals(testLastName, user.getLastName());
    }

    @Test
    void shouldConvertDTOToUserAndReturnCorrectPatronymic() {
        UserDTO userDTO = new UserDTO();
        String testPatronymic = "ivanych";
        userDTO.setPatronymic(testPatronymic);
        User user = userServiceConverter.convertDTOtoUser(userDTO);
        Assertions.assertEquals(testPatronymic, user.getPatronymic());
    }

    @Test
    void shouldConvertDTOToUserAndReturnCorrectUsername() {
        UserDTO userDTO = new UserDTO();
        String testUsername = "ivan@gmail.com";
        userDTO.setUsername(testUsername);
        User user = userServiceConverter.convertDTOtoUser(userDTO);
        Assertions.assertEquals(testUsername, user.getUsername());
    }

    @Test
    void shouldConvertDTOToUserAndReturnCorrectRole() {
        Role role = new Role();
        role.setName(RoleEnum.ROLE_SALE_USER);
        when(roleRepository.findRoleByName(RoleEnum.ROLE_SALE_USER)).thenReturn(role);
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(role.getName());
        User user = userServiceConverter.convertDTOtoUser(userDTO);
        Assertions.assertEquals(RoleEnum.ROLE_SALE_USER, user.getRole().getName());
    }
}