package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String username;
    private RoleEnum role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
