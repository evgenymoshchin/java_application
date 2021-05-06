package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
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
    @NotNull
    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = "^[A-Za-z]*$", message = "Should be only latin characters")
    private String patronymic;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String username;
    private RoleEnum role;
}
