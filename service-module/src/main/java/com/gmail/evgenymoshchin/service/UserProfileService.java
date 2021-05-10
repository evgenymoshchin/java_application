package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.UserProfileDTO;

public interface UserProfileService {

    UserProfileDTO getById(Long id);

    UserProfileDTO findUserProfileByUsername(String name);

    void updateUserProfile(UserProfileDTO userProfileDTO);
}
