package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode(exclude = {"post", "author"})
@Table(name = "comment")
public class Comment extends BaseEntity{

    @Column(name = "text")
    private String text;

    @OneToOne
    private User author;

    @ManyToOne
    private Post post;

}
