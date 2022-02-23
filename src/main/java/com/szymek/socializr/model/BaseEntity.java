package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    //TODO: -add create date -add last modification date

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
