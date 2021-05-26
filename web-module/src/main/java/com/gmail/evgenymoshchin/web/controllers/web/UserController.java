package com.gmail.evgenymoshchin.web.controllers.web;

import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.service.model.UserPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_SIZE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE_SIZE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.ROLES_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.ROLE_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.SELECTED_USERS_PARAMETER_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.USERS_GET_REDIRECTION_URL;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/add")
    public String addUserPage(UserDTO userDTO, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        return "add_user";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        if (!bindingResult.hasErrors()) {
            userService.addUser(userDTO);
            return USERS_GET_REDIRECTION_URL;
        } else {
            return "add_user";
        }
    }

    @GetMapping("/get")
    public String getUsers(Model model,
                           @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE_VALUE) int pageSize,
                           @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_VALUE) int pageNumber
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
        return USERS_GET_REDIRECTION_URL;
    }

    @GetMapping("/update-role/{id}")
    public String getUpdateUserRolePage(@PathVariable Long id, Model model, UserDTO userDTO) {
        model.addAttribute(ROLES_ATTRIBUTE_VALUE, roleService.findAll());
        return "update_user";
    }

    @PostMapping("/update-role")
    public String updateUserRoleById(UserDTO userDTO,
                                     @RequestParam(value = ROLE_ATTRIBUTE_VALUE) RoleEnum roleEnum) {
        userService.updateUserRoleById(userDTO.getId(), roleEnum);
        return USERS_GET_REDIRECTION_URL;
    }

    @GetMapping("/update-password/{id}")
    public String updatePasswordById(@PathVariable Long id) {
        userService.updatePasswordById(id);
        return USERS_GET_REDIRECTION_URL;
    }
}
