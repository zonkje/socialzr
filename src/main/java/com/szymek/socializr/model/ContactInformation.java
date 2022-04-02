package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "contact_information")
public class ContactInformation extends BaseEntity {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is invalid")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "\\d{9}|(?:\\d{3}-){2}\\d{3}")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder.Default
    @ElementCollection
    private List<String> websitesURLs = new ArrayList<>();

    @Embedded
    private Address address;

}
