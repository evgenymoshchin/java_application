package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.UserProfileService;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserService userService;

    public UserProfileController(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @GetMapping("/get")
    public String getUserProfile(Principal principal, Model model) {
        UserProfileDTO profile = userProfileService.findUserProfileByUsername(principal.getName());
        model.addAttribute("profile", profile);
        return "get_user_profile";
    }

    @GetMapping("/update/{id}")
    public String getUserProfilePage(@PathVariable Long id, Model model) {
        model.addAttribute("profile", userProfileService.getById(id));
        return "update_profile";
    }

    @PostMapping("/update")
    public String updateUserProfile(@Valid UserProfileDTO profile, BindingResult bindingResult, Model model) {
        model.addAttribute("profile", profile);
        if (!bindingResult.hasErrors()) {
            userProfileService.updateUserProfile(profile);
            return "redirect:/profiles/get";
        }
        return "update_profile";
    }

    @GetMapping("/update-password/{id}")
    public String updatePasswordById(@PathVariable Long id) {
        userService.updatePasswordById(id);
        return "redirect:/profiles/get";
    }
}
