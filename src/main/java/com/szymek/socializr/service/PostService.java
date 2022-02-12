package com.szymek.socializr.service;

import com.szymek.socializr.model.Post;

import java.util.Collection;

public interface PostService extends CrudService<Post, Long>{

    Collection<Post> findAllByAuthor();

}
