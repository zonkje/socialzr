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
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode(exclude = {"members", "creator"})
@Table(name = "social_group")
public class SocialGroup extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "user_social_group",
            joinColumns = @JoinColumn(name = "social_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> members;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;

}
