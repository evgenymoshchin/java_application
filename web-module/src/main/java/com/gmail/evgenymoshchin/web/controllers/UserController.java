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

import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ADD_USERS_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ADD_USER_VIEW_NAME_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ERROR_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.EXIST_USER_MESSAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.GET_USERS_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.GET_USERS_VIEW_NAME_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.REMOVE_USER_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ROLES_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ROLE_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.SELECTED_USERS_PARAMETER_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.UPDATE_USER_PASSWORD_URL_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.UPDATE_USER_ROLE_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.UPDATE_USER_VIEW_NAME_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.USERS_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.USERS_GET_REDIRECTION_PATH_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.USER_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.USER_CONTROLLER_MAPPING_VALUE;

@Controller
@RequestMapping(USER_CONTROLLER_MAPPING_VALUE)
public class UserController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());


    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(ADD_USERS_MAPPING_VALUE)
    public String addUserPage(UserDTO userDTO, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        return ADD_USER_VIEW_NAME_VALUE;
    }

    @PostMapping(ADD_USERS_MAPPING_VALUE)
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        if (!bindingResult.hasErrors()) {
            try {
                userService.addUser(userDTO);
                return USERS_GET_REDIRECTION_PATH_VALUE;
            } catch (UserAlreadyExistException exception) {
                logger.error(exception.getMessage(), exception);
                model.addAttribute(ERROR_ATTRIBUTE_VALUE, EXIST_USER_MESSAGE_VALUE);
                return ADD_USER_VIEW_NAME_VALUE;
            }
        } else {
            return ADD_USER_VIEW_NAME_VALUE;
        }
    }

    @GetMapping(GET_USERS_MAPPING_VALUE)
    public String getAllUsers(Model model, Principal principal) {
        List<UserDTO> users = userService.findAll();
        List<UserDTO> usersWithoutCurrent = new ArrayList<>();
        for (UserDTO user : users) {
            if (!user.getUsername().equals(principal.getName())) {
                usersWithoutCurrent.add(user);
            }
        }
        usersWithoutCurrent.sort(Comparator.comparing(UserDTO::getUsername));
        model.addAttribute(USERS_ATTRIBUTE_VALUE, usersWithoutCurrent);
        return GET_USERS_VIEW_NAME_VALUE;
    }

    @PostMapping(REMOVE_USER_MAPPING_VALUE)
    public String removeUserById(@RequestParam(value = SELECTED_USERS_PARAMETER_VALUE,
            required = false) List<Long> ids) {
        if (ids != null) {
            for (Long id : ids) {
                userService.removeUserById(id);
            }
        }
        return USERS_GET_REDIRECTION_PATH_VALUE;
    }

    @GetMapping(UPDATE_USER_ROLE_MAPPING_VALUE)
    public String getUpdateUserRolePage(@PathVariable Long id, Model model, UserDTO userDTO) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        model.addAttribute(USER_ATTRIBUTE_VALUE, userDTO);
        return UPDATE_USER_VIEW_NAME_VALUE;
    }

    @PostMapping(UPDATE_USER_ROLE_MAPPING_VALUE)
    public String updateUserRoleById(@PathVariable Long id,
                                     @RequestParam(value = ROLE_ATTRIBUTE_VALUE) RoleEnum roleEnum) {
        userService.updateUserRoleById(id, roleEnum);
        return USERS_GET_REDIRECTION_PATH_VALUE;
    }

    @GetMapping(UPDATE_USER_PASSWORD_URL_VALUE)
    public String updatePasswordById(@PathVariable Long id) {
        userService.updatePasswordById(id);
        return USERS_GET_REDIRECTION_PATH_VALUE;
    }
}
