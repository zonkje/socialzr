package com.szymek.socializr.service;

import com.szymek.socializr.model.Post;

import java.util.Collection;

public interface PostService {

    Post findById(Long id);

    Post save(Post post);

    Collection<Post> findAll();

    Collection<Post> findAllByAuthor();

}
