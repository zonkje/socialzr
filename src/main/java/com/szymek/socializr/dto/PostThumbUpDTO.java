package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostThumbUpDTO extends BaseEntityDTO {

    private Long authorId;

    @NotNull(message = "Thumb Up must be assigned to the post")
    private Long postId;

}
