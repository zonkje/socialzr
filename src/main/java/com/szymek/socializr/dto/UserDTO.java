package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserDTO extends BaseEntityDTO {

    @Size(min = 2, message = "Username must be equal or greater than 2 characters")
    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String avatarUrl;

    private Long contactInformationId;

}
