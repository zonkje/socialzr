package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
@Table(name = "user")
public class User extends BaseEntity {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must be equal or greater than 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_information_id")
    private ContactInformation contactInformation;

    //    @JsonManagedReference
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL)
    private Collection<Post> posts;

    @ManyToMany(mappedBy = "members")
    private Collection<SocialGroup> socialGroups;

}


