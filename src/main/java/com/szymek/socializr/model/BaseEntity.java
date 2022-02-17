package com.szymek.socializr.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    //TODO: -add create date -add last modification date

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
