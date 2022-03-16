package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CommentThumbUpDTO extends BaseEntityDTO {

    @NotNull(message = "Thumb Up author ID cannot be null")
    private Long authorId;

    @NotNull(message = "Thumb Up must be assigned to the post")
    private Long commentId;

}
