package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserInformation;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceConverterImplTest {

    @InjectMocks
    private UserProfileServiceConverterImpl userProfileServiceConverter;

    @Test
    void shouldConvertUserToUserProfileDTOAndReturnNotNullObject() {
        User user = new User();
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertNotNull(profile);
    }

    @Test
    void shouldConvertUserToUserProfileDTOAndReturnCorrectId() {
        Long testId = 1L;
        User user = new User();
        user.setId(testId);
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertEquals(testId, profile.getId());
    }

    @Test
    void shouldConvertUserToUserProfileDTOAndReturnCorrectFirstName() {
        String testFirstName = "test";
        User user = new User();
        user.setFirstName(testFirstName);
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertEquals(testFirstName, profile.getFirstName());
    }

    @Test
    void shouldConvertUserToUserProfileDTOAndReturnCorrectLastName() {
        String testLastName = "test";
        User user = new User();
        user.setLastName(testLastName);
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertEquals(testLastName, profile.getLastName());
    }

    @Test
    void shouldConvertUserInformationToUserProfileDTOAndReturnCorrectAddress() {
        String testAddress = "test";
        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(testAddress);
        User user = new User();
        user.setUserInformation(userInformation);
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertEquals(testAddress, profile.getAddress());
    }

    @Test
    void shouldConvertUserInformationToUserProfileDTOAndReturnCorrectTelephone() {
        String testTelephone = "test";
        UserInformation userInformation = new UserInformation();
        userInformation.setTelephone(testTelephone);
        User user = new User();
        user.setUserInformation(userInformation);
        UserProfileDTO profile = userProfileServiceConverter.convertUserProfileToDTO(user);
        Assertions.assertEquals(testTelephone, profile.getTelephone());
    }
}