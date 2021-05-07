package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserPageDTO;
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
import java.util.List;

import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ERROR_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.EXIST_USER_MESSAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ROLES_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.ROLE_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.SELECTED_USERS_PARAMETER_VALUE;
import static com.gmail.evgenymoshchin.web.constants.UserControllerConstants.USER_ATTRIBUTE_VALUE;

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
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        return "add_user";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        if (!bindingResult.hasErrors()) {
            try {
                userService.addUser(userDTO);
                return "redirect:/users/get";
            } catch (UserAlreadyExistException exception) {
                logger.error(exception.getMessage(), exception);
                model.addAttribute(ERROR_ATTRIBUTE_VALUE, EXIST_USER_MESSAGE_VALUE);
                return "add_user";
            }
        } else {
            return "add_user";
        }
    }

    @GetMapping("/get")
    public String getUsers(Model model,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber
    ) {
        UserPageDTO userPage = userService.findUsersWithPagination(pageNumber, pageSize);
        model.addAttribute("userPage", userPage);
        return "get_all_users";
    }

    @PostMapping("/remove")
    public String removeUserById(@RequestParam(value = SELECTED_USERS_PARAMETER_VALUE,
            required = false) List<Long> ids) {
        if (ids != null) {
            for (Long id : ids) {
                userService.removeUserById(id);
            }
        }
        return "redirect:/users/get";
    }

    @GetMapping("/update-role/{id}")
    public String getUpdateUserRolePage(@PathVariable Long id, Model model, UserDTO userDTO) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        model.addAttribute(USER_ATTRIBUTE_VALUE, userDTO);
        return "update_user";
    }

    @PostMapping("/update-role/{id}")
    public String updateUserRoleById(@PathVariable Long id,
                                     @RequestParam(value = ROLE_ATTRIBUTE_VALUE) RoleEnum roleEnum) {
        userService.updateUserRoleById(id, roleEnum);
        return "redirect:/users/get";
    }

    @GetMapping("/update-password/{id}")
    public String updatePasswordById(@PathVariable Long id) {
        userService.updatePasswordById(id);
        return "redirect:/users/get";
    }
}
