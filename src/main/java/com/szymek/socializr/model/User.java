package com.szymek.socializr.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "user")
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactInformation contactInformation;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL)
    private Collection<Post> posts;

    @ManyToMany(mappedBy = "members")
    private Collection<SocialGroup> socialGroups;

}


