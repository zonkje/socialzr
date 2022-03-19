package com.szymek.socializr.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Column(name = "address")
    private String address;

    @Size(min = 2, message = "City name should be at least 2 characters long")
    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Size(min = 2, message = "State name should be at least 2 characters long")
    @Column(name = "state")
    private String state;

    @Size(min = 4, message = "Country name should be at least 4 characters long")
    @Column(name = "country")
    private String country;

}
