package com.devaon.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qwone4@gmail.com on 2020-05-12
 * Blog : http://aonee.tistory.com
 * Github : http://github.com/devAon
 */
@RestController
public class LoginController {
    @GetMapping("/main")
    public String main(){
        return "Welcome!! This is main page";
    }


}
