package com.szymek.socializr.security;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.validation.PasswordStrength;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SignUpRequest extends UserDTO {

    @NotBlank
    @Size(min = 2, message = "Username must be equal or greater than 2 characters")
    private String username;

    @NotBlank
    @PasswordStrength
    private String password;
}
