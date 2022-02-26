package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode(exclude = {"author"})
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
@Table(name = "post")
public class Post extends BaseEntity{

    @NotBlank(message = "Post text cannot be blank")
    @Lob
    @Column(name = "text")
    private String text;

    @NotNull(message = "Post author cannot be null")
    @ManyToOne
    @JoinColumn(name = "post_author_id")
    private User author;

//    @JsonBackReference
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    private Collection<Comment> comments;

}
