package com.szymek.socializr.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CommentDTO extends TextWidgetDTO {

    @NotNull(message = "Comment must be assigned to the post")
    private Long postId;

    private Integer commentThumbUpCount;

}
