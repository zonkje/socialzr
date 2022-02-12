package com.szymek.socializr.model;

import javax.persistence.*;
import java.util.Collection;

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

    public SocialGroup() {
    }

    public SocialGroup(String name, String description, User creator, Collection<User> members) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.members = members;
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

    @Override
    public String toString() {
        return "SocialGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialGroup group = (SocialGroup) o;

        return id != null ? id.equals(group.id) : group.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
