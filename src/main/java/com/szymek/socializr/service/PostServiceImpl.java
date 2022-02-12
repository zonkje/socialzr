package com.szymek.socializr.service;

import com.szymek.socializr.model.Post;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostServiceImpl implements PostService{

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Collection<Post> findAll() {
        return null;
    }

    @Override
    public Collection<Post> findAllByAuthor() {
        return null;
    }

}
