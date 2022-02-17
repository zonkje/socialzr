package com.szymek.socializr.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class SocialGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "user_socialGroup",
            joinColumns = @JoinColumn(name = "socialGroup_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> members;

    @Enumerated(value = EnumType.STRING)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "SocialGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", members=" + members +
                ", accessLevel=" + accessLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialGroup that = (SocialGroup) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(creator, that.creator) && Objects.equals(members, that.members) && accessLevel == that.accessLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creator, members, accessLevel);
    }
}
