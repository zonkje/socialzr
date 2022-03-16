package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class TextWidget extends BaseEntity {

    @NotBlank(message = "Text cannot be blank")
    @Column(name = "text")
    private String text;

    @NotNull(message = "Author cannot be null")
    @ManyToOne
    private User author;

}
