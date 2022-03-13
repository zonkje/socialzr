package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode(exclude = {"post", "author"})
@Table(name = "comment")
public class Comment extends TextWidget{

    @NotNull(message = "Comment must be assigned to the post")
    @ManyToOne
    private Post post;

}
