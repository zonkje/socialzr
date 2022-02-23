package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CommentDTO extends BaseEntityDTO {

    private String text;

    private Long authorId;

    private Long postId;

}
