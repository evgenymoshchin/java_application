package com.gmail.evgenymoshchin.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeniedController {

    @GetMapping("/access-denied")
    public String deniedPage() {
        return "denied";
    }
}
