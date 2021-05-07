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
        user.getUserInformation().setAddress(userProfileDTO.getAddress());
        user.getUserInformation().setTelephone(userProfileDTO.getTelephone());
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

//    private User convertDTOtoUser(UserDTO userDTO) {
//        User user = new User();
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setPatronymic(userDTO.getPatronymic());
//        user.setUsername(userDTO.getUsername());
//        Role role = roleRepository.findByRoleByName(userDTO.getRole());
//        user.setRole(role);
//        String generatedPassword = passwordService.generateRandomPassword();
//        user.setPassword(passwordEncoder.encode(generatedPassword));
//        sendEmail(userDTO.getUsername(), generatedPassword);
//        return user;
//    }
}
