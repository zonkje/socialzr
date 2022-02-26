package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CommentDTO extends BaseEntityDTO {

    @NotBlank(message = "Comment text cannot be blank")
    private String text;

    @NotNull(message = "Comment author ID cannot be null")
    private Long authorId;

    @NotNull(message = "Comment must be assigned to the post")
    private Long postId;

}
