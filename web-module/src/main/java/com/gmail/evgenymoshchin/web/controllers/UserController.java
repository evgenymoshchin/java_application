package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

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
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_user";
        } else {
            userService.addUser(userDTO);
            return "redirect:/login";
        }
    }

    @GetMapping("/get")
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.findAll();
        users.sort(Comparator.comparing(UserDTO::getUsername));
        model.addAttribute("users", users);
        return "get_all_users";
    }

    @PostMapping("/get")
    public String removeUserById(@RequestParam(name = "selectedUsers", required = false) List<Long> ids) {
        for (Long id : ids) {
            userService.removeUserById(id);
        }
        return "redirect:/users/get";
    }
}
