package com.szymek.socializr.dto;

import com.szymek.socializr.model.Address;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ContactInformationDTO extends BaseEntityDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is invalid")
    private String email;

    @Pattern(regexp = "\\d{9}|(?:\\d{3}-){2}\\d{3}")
    private String phoneNumber;

    private Address address;

}
