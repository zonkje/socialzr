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

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must be equal or greater than 2 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private Long contactInformationId;

}
