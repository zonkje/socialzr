package com.szymek.socializr.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

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

    public User() {
    }

    public User(String firstName, String lastName, ContactInformation contactInformation, Collection<Post> posts, Collection<SocialGroup> socialGroups) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInformation = contactInformation;
        this.posts = posts;
        this.socialGroups = socialGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Collection<SocialGroup> getSocialGroups() {
        return socialGroups;
    }

    public void setSocialGroups(Collection<SocialGroup> groups) {
        this.socialGroups = groups;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactInformation=" + contactInformation +
                ", posts=" + posts +
                ", groups=" + socialGroups +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
