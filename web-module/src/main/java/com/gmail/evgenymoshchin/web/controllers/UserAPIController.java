package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAPIController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            user.setRole(RoleEnum.ROLE_SECURE_API_USER);
            userService.addUser(user);
            log.debug("Added user with username {}", user.getUsername());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
