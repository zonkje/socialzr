package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

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

    @ManyToMany
    @JoinTable(
            name = "post_post_label",
            joinColumns = @JoinColumn(name = "post_label_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @Builder.Default
    private Collection<Post> posts = new ArrayList<>();

}
