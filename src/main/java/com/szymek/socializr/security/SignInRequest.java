package com.szymek.socializr.security;

import lombok.Data;

@Data
public class SignInRequest {

    private String username;
    private String password;

}
