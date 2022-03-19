package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SocialGroupPostDTO extends PostDTO{

    Long socialGroupId;

}
