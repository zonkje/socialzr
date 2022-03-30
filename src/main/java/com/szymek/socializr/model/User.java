package com.szymek.socializr.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
    //TODO -got exceptions after add UNIQUE constraint to column 'username'
@Table(name = "user"
//        ,uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}
)
public class User extends BaseEntity implements UserDetails {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must be equal or greater than 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "contact_information_id")
    private ContactInformation contactInformation;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL)
    private Collection<Post> posts;

    @ManyToMany(mappedBy = "members")
    private Collection<SocialGroup> socialGroups;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


