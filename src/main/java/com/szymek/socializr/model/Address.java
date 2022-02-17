package com.szymek.socializr.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

}
