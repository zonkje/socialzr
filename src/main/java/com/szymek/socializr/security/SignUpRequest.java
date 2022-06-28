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
    @PasswordStrength
    private String password;
}
