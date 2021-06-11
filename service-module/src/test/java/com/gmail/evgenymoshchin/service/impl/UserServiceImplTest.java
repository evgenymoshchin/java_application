package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.converters.UserServiceConverter;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceConverter converter;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldFindUserByUsername() {
        String userName = "ivan@gmail.com";
        User user = new User();
        user.setUsername(userName);

        when(userRepository.findByUsername(userName)).thenReturn(user);

        assertEquals(userService.findUserByUsername(user.getUsername()), user);
    }
}
