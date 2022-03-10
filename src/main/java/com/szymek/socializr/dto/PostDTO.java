package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends BaseEntityDTO {

    //TODO: -add author name field -add number of comments field

    @NotBlank(message = "Post text cannot be blank")
    private String text;

    @NotNull(message = "Post author ID cannot be null")   //TODO -provide this in the future using Principals
    private Long authorId;

}
