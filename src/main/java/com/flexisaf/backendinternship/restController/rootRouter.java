package com.flexisaf.backendinternship.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rootRouter {
    @GetMapping("/")
    public String root() {
        return "Spring boot server is running";
    }
}
