package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SocialGroupDTO extends BaseEntityDTO {

    private String name;

    private String description;

    private Long creatorId;

    private String accessLevel;

}
