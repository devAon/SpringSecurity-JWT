package com.devaon.jwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qwone4@gmail.com on 2020-05-23
 * Blog : http://aonee.tistory.com
 * Github : http://github.com/devAon
 */
@RestController
@CrossOrigin
public class HelloWorldController {
    @RequestMapping({"/hello"})
    public String firstPage(){
        return "Hello. you have valid JWT (JSon Web Token)!";
    }
}
