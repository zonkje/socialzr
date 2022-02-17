package com.szymek.socializr.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "social_group")
public class SocialGroup extends BaseEntity{

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

    public SocialGroup() {
    }

    public SocialGroup(String name, String description, User creator, Collection<User> members, AccessLevel accessLevel) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.members = members;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

}
