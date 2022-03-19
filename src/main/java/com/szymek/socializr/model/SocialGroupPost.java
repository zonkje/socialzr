package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "social_group_post")
public class SocialGroupPost extends Post{

    @NotNull(message = "Social group cannot be null")
    @ManyToOne
    private SocialGroup socialGroup;

}
