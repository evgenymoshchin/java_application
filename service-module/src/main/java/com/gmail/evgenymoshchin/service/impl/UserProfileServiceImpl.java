package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.repository.model.UserInformation;
import com.gmail.evgenymoshchin.service.UserProfileService;
import com.gmail.evgenymoshchin.service.converters.UserProfileServiceConverter;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.USER_WAS_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileServiceConverter converter;

    @Override
    @Transactional
    public UserProfileDTO getById(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            return converter.convertUserProfileToDTO(user);
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public UserProfileDTO findUserProfileByUsername(String name) {
        User user = userRepository.findByUsername(name);
        if (Objects.nonNull(user)) {
            return converter.convertUserProfileToDTO(user);
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, name));
        }
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(userProfileDTO.getId());
        if (Objects.nonNull(user)) {
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
        } else {
            throw new ServiceException(String.format(USER_WAS_NOT_FOUND_MESSAGE, userProfileDTO.getId()));
        }
    }
}
