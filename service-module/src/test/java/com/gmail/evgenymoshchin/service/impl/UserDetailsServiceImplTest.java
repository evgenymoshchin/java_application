package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserLogin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldNotReturnEqualsUserLoginBecauseMethodReturnNew() {
        String userName = "ivan@gmail.com";
        User user = new User();
        user.setUsername(userName);
        UserLogin userLogin = new UserLogin(user);

        when(userService.findUserByUsername(userName)).thenReturn(user);

        assertNotEquals((userDetailsService.loadUserByUsername(user.getUsername())), userLogin);
    }
}