package com.szymek.socializr.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"post", "author"})
@Table(name = "comment")
public class Comment extends BaseEntity{

    @Column(name = "text")
    private String text;

    @OneToOne
    private User author;

    @ManyToOne
    private Post post;

}
