package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserInformation;
import com.gmail.evgenymoshchin.service.UserProfileService;
import com.gmail.evgenymoshchin.service.converters.UserProfileServiceConverter;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileServiceConverter converter;

    @Override
    @Transactional
    public UserProfileDTO getById(Long id) {
        User user = userRepository.findById(id);
        return converter.convertUserProfileToDTO(user);
    }

    @Override
    @Transactional
    public UserProfileDTO findUserProfileByUsername(String name) {
        User user = userRepository.findByUsername(name);
        return converter.convertUserProfileToDTO(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(userProfileDTO.getId());
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        if (Objects.nonNull(user.getUserInformation())) {
            user.getUserInformation().setAddress(userProfileDTO.getAddress());
            user.getUserInformation().setTelephone(userProfileDTO.getTelephone());
        } else {
            UserInformation userInformation = new UserInformation();
            userInformation.setAddress(userProfileDTO.getAddress());
            userInformation.setTelephone(userProfileDTO.getTelephone());
            userInformation.setUser(user);
            user.setUserInformation(userInformation);
        }
    }
}
