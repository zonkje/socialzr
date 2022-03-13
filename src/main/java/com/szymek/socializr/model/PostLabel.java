package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "post_label")
public class PostLabel extends BaseEntity{

    @NotBlank(message = "Label name cannot be blank")
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
