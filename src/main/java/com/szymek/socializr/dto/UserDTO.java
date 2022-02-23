package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserDTO extends BaseEntityDTO {

    private String firstName;

    private String lastName;

    private Long contactInformationId;

}
