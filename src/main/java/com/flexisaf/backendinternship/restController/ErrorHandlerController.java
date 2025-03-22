package com.flexisaf.backendinternship.restController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ErrorHandlerController implements ErrorController{
    @RequestMapping("error")
    public String handleError() {
        return "Not found";
    }
}
