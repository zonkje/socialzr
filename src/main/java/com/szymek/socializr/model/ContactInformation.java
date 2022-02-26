package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact_information")
public class ContactInformation extends BaseEntity{

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is invalid")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "\\d{9}|(?:\\d{3}-){2}\\d{3}")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

}
