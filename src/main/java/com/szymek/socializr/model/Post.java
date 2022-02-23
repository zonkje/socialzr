package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @Lob
    @Column(name = "text")
    private String text;

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
