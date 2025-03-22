package com.flexisaf.backendinternship.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public String root() {
        return "Spring boot server is running";
    }
}
