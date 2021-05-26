package com.gmail.evgenymoshchin.web.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.gmail.evgenymoshchin.web.constants.DeniedAndLoginControllersConstants.LOGIN_PAGE_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.DeniedAndLoginControllersConstants.LOGIN_VIEW_NAME_VALUE;

@Controller
public class LoginController {

    @GetMapping(LOGIN_PAGE_MAPPING_VALUE)
    public String getLoginPage() {
        return LOGIN_VIEW_NAME_VALUE;
    }
}
