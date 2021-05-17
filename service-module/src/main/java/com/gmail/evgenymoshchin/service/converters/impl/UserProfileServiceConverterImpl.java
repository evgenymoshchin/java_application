package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.converters.UserProfileServiceConverter;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserProfileServiceConverterImpl implements UserProfileServiceConverter {

    @Override
    public UserProfileDTO convertUserProfileToDTO(User user) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(user.getId());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setLastName(user.getLastName());
        if (Objects.nonNull(user.getUserInformation())) {
            userProfileDTO.setAddress(user.getUserInformation().getAddress());
            userProfileDTO.setTelephone(user.getUserInformation().getTelephone());
        }
        return userProfileDTO;
    }
}
