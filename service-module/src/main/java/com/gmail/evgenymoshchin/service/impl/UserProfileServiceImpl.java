package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserInformation;
import com.gmail.evgenymoshchin.service.UserProfileService;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    public UserProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserProfileDTO getById(Long id) {
        User user = userRepository.findById(id);
        return convertUserProfileToDTO(user);
    }

    @Override
    @Transactional
    public UserProfileDTO findUserProfileByUsername(String name) {
        User user = userRepository.findByUsername(name);
        return convertUserProfileToDTO(user);
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

    private UserProfileDTO convertUserProfileToDTO(User user) {
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
