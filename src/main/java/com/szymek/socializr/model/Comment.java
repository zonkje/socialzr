package com.szymek.socializr.model;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{

    @Column(name = "text")
    private String text;

    @OneToOne
    private User author;

    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Comment(String text, User author, Post post) {
        this.text = text;
        this.author = author;
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
