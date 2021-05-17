package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.evgenymoshchin.service.constants.DTOConstants.MESSAGE_WHEN_NOT_LATIN_CHARS;
import static com.gmail.evgenymoshchin.service.constants.DTOConstants.PHONE_NUMBER_NOT_VALID_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.DTOConstants.PHONE_NUMBER_REGEX;
import static com.gmail.evgenymoshchin.service.constants.DTOConstants.REGEX_FOR_LATIN_CHARS;

@Data
public class UserProfileDTO {
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
    @Size(max = 40)
    @Pattern(regexp = REGEX_FOR_LATIN_CHARS, message = MESSAGE_WHEN_NOT_LATIN_CHARS)
    private String address;
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_NOT_VALID_MESSAGE)
    private String telephone;
}
