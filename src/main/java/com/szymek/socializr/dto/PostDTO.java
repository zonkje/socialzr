package com.szymek.socializr.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends TextWidgetDTO {

    //TODO: -add author name field -add number of comments field

    List<String> postLabels;

    private Integer postThumbUpCount;

}
