package com.szymek.socializr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "social_group")
public class SocialGroup extends BaseEntity {

    @NotBlank(message = "Group name cannot be blank")
    @Size(min = 3, max = 48, message = "Group name must be equal or greater than 3 and less than 48 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Group description cannot be blank")
    @Column(name = "description")
    private String description;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @NotNull
    @OneToOne
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "user_social_group",
            joinColumns = @JoinColumn(name = "social_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> members;

    @OneToMany(
            mappedBy = "socialGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<SocialGroupPost> socialGroupPosts;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;

}
