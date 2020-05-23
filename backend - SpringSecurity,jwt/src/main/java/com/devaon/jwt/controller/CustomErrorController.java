package com.devaon.jwt.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qwone4@gmail.com on 2020-05-13
 * Blog : http://aonee.tistory.com
 * Github : http://github.com/devAon
 */
@RestController
@CrossOrigin
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(){
        return "/index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
