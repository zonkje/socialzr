package com.szymek.socializr.security;

import com.szymek.socializr.dto.UserDTO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SignUpRequest extends UserDTO {

    private String username;
    private String password;
}
