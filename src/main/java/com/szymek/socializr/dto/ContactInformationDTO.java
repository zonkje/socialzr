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

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{9}|(?:\\d{3}-){2}\\d{3}", message = "TO MUSI BYC TELEFON")
    private String phoneNumber;

    private String linkedinUrl;

    private Address address;

}
