package com.szymek.socializr.security;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SignInRequest {

    private String username;
    private String password;

}
