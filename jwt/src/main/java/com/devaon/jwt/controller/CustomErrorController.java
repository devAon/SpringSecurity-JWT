package com.devaon.jwt.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qwone4@gmail.com on 2020-05-13
 * Blog : http://aonee.tistory.com
 * Github : http://github.com/devAon
 */
@Controller
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
