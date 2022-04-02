package com.szymek.socializr.dto;

import com.szymek.socializr.model.Address;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

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
    @Pattern(regexp = "\\d{9}|(?:\\d{3}-){2}\\d{3}", message = "The phone number must match the patterns: " +
            "xxxxxxxxx or xxx-xxx-xxx")
    private String phoneNumber;

    private List<String> websitesURLs;

    private Address address;

}
