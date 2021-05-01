package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static final String EXIST_USER_MESSAGE = "An user for that email is already exists.";

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/add")
    public String addUserPage(UserDTO userDTO, Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "add_user";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        model.addAttribute("roles", roleService.findAll());
        if (!bindingResult.hasErrors()) {
            try {
                userService.addUser(userDTO);
                return "redirect:/users/get";
            } catch (UserAlreadyExistException exception) {
                model.addAttribute("error", EXIST_USER_MESSAGE);
                return "add_user";
            }
        } else {
            return "add_user";
        }
    }

    @GetMapping("/get")
    public String getAllUsers(Model model, Principal principal) {
        List<UserDTO> users = userService.findAll();
        List<UserDTO> usersWithoutCurrent = new ArrayList<>();
        for (UserDTO user : users) {
            if (!user.getUsername().equals(principal.getName())) {
                usersWithoutCurrent.add(user);
            }
        }
        usersWithoutCurrent.sort(Comparator.comparing(UserDTO::getUsername));
        model.addAttribute("users", usersWithoutCurrent);
        return "get_all_users";
    }

    @PostMapping("/remove")
    public String removeUserById(@RequestParam(value = "selectedUsers", required = false) List<Long> ids) {
        if (ids != null) {
            for (Long id : ids) {
                userService.removeUserById(id);
            }
        }
        return "redirect:/users/get";
    }

    @GetMapping("/update-role/{id}")
    public String getUpdateUserRolePage(@PathVariable Long id, Model model, UserDTO userDTO) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userDTO);
        return "update_user";
    }

    @PostMapping("/update-role/{id}")
    public String updateUserRoleById(@PathVariable Long id, @RequestParam(value = "role") RoleEnum roleEnum) {
        userService.updateUserRoleById(id, roleEnum);
        return "redirect:/users/get";
    }

    @GetMapping("/update-password/{id}")
    public String updatePasswordById(@PathVariable Long id) {
        userService.updatePasswordById(id);
        return "redirect:/users/get";
    }
}
