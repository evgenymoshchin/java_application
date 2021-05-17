package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.UserDTO;

public interface UserServiceConverter {
    UserDTO convertUserToDTO(User user);

    User convertDTOtoUser(UserDTO userDTO);
}
