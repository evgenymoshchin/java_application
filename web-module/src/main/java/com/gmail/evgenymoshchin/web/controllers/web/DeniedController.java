package com.gmail.evgenymoshchin.web.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.gmail.evgenymoshchin.web.constants.DeniedAndLoginControllersConstants.DENIED_PAGE_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.DeniedAndLoginControllersConstants.DENIED_VIEW_NAME_VALUE;

@Controller
public class DeniedController {

    @GetMapping(DENIED_PAGE_MAPPING_VALUE)
    public String deniedPage() {
        return DENIED_VIEW_NAME_VALUE;
    }
}
