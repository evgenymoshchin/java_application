package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.UserProfileService;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;
import java.security.Principal;

@Controller
@RequestMapping("/profiles")
public class UserProfileController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/get")
    public String getUserProfile(Principal principal, Model model) {
//        UserProfileDTO userProfileDTO = userProfileService.findUserProfileByUsername(principal.getName());
        UserProfileDTO profile = userProfileService.findUserProfileByUsername("ztrancer@gmail.com");
//        logger.info(principal.getName());
        model.addAttribute("profile", profile);
        return "get_user_profile";
    }

    @GetMapping("/update/{id}")
    public String getUserProfilePage(@PathVariable Long id, Model model) {
        model.addAttribute("profile", userProfileService.getById(id));
        return "update_profile";
    }

    //TODO update password functionality
    @PostMapping("/update")
    public String updateUserProfile(UserProfileDTO userProfileDTO) {
        userProfileService.updateUserProfile(userProfileDTO);
        logger.info(userProfileDTO);
        return "redirect:/profiles/get";
    }
}
