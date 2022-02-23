package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends BaseEntityDTO {

    //TODO: -add author name field -add number of comments field

    private String text;

    private Long authorId;

}
