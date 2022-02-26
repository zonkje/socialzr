package com.szymek.socializr.repository;

import com.szymek.socializr.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Collection<Post> findAll();

}
