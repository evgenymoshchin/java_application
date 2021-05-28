package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.converters.UserProfileServiceConverter;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProfileServiceConverter converter;
    @InjectMocks
    UserProfileServiceImpl userProfileService;

    @Test
    void shouldFindProfileById() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(user);
        when(converter.convertUserProfileToDTO(user)).thenReturn(profileDTO);

        assertEquals(userProfileService.getById(user.getId()), profileDTO);
    }

    @Test
    void shouldFindProfileByUsername() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        String userName = "ivan@gmail.com";
        String firstName = "ivan";
        User user = new User();
        user.setUsername(userName);
        user.setFirstName(firstName);

        when(userRepository.findByUsername(userName)).thenReturn(user);
        when(converter.convertUserProfileToDTO(user)).thenReturn(profileDTO);

        assertEquals(userProfileService.findUserProfileByUsername(user.getUsername()), profileDTO);
    }
}