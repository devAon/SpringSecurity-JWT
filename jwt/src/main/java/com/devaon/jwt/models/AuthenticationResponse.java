package com.devaon.jwt.models;

import lombok.Getter;

/**
 * Created by qwone4@gmail.com on 2020-05-12
 * Blog : http://aonee.tistory.com
 * Github : http://github.com/devAon
 */
@Getter
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
