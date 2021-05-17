package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.evgenymoshchin.service.constants.DTOConstants.EMAIL_NOT_VALID_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.DTOConstants.MESSAGE_WHEN_NOT_LATIN_CHARS;
import static com.gmail.evgenymoshchin.service.constants.DTOConstants.REGEX_FOR_LATIN_CHARS;

@Data
public class UserDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = REGEX_FOR_LATIN_CHARS, message = MESSAGE_WHEN_NOT_LATIN_CHARS)
    private String firstName;
    @NotNull
    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = REGEX_FOR_LATIN_CHARS, message = MESSAGE_WHEN_NOT_LATIN_CHARS)
    private String lastName;
    @NotNull
    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = REGEX_FOR_LATIN_CHARS, message = MESSAGE_WHEN_NOT_LATIN_CHARS)
    private String patronymic;
    @NotEmpty
    @Email(message = EMAIL_NOT_VALID_MESSAGE)
    private String username;
    private RoleEnum role;
}
