package com.szymek.socializr.dto;

import com.szymek.socializr.model.Address;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ContactInformationDTO extends BaseEntityDTO {

    private String email;

    private String phoneNumber;

    private Address address;

}
