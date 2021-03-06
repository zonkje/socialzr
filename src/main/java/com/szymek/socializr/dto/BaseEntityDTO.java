package com.szymek.socializr.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityDTO implements Serializable {

    private Long id;
    private Instant createDate;
    private Instant lastModifiedDate;

}
