package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO REGEXP FOR THE ADDRESS AND PHONE!!!!!!!!
@Data
public class UserProfileDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z]*$", message = "Should be only latin characters")
    private String firstName;
    @NotNull
    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = "^[A-Za-z]*$", message = "Should be only latin characters")
    private String lastName;
    private String address;
    private String telephone;
}
