package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "post_thumb_up")
public class PostThumbUp extends BaseEntity {

    //TODO: -ignore createDate & lastModifiedDate
    @ManyToOne
    private Post post;

    @ManyToOne
    private User author;

}
