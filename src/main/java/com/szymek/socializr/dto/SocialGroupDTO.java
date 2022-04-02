package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SocialGroupDTO extends BaseEntityDTO {

    @NotBlank(message = "Group name cannot be blank")
    @Size(min = 3, max = 48, message = "Group name must be equal or greater than 3 and less than 48 characters")
    private String name;

    @NotBlank(message = "Group description cannot be blank")
    private String description;

    private String avatarUrl;

    @NotNull
    private Long creatorId;

    @NotNull
    private String accessLevel;

}
