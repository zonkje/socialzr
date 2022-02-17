package com.szymek.socializr.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact_information")
public class ContactInformation extends BaseEntity{

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

}
