package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;

public interface UserProfileServiceConverter {
    UserProfileDTO convertUserProfileToDTO(User user);
}
