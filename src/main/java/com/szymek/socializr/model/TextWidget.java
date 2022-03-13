package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
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

    @NotBlank(message = "Comment text cannot be blank")
    @Column(name = "text")
    private String text;

    @NotNull(message = "Comment author cannot be null")
    @OneToOne
    private User author;

}
