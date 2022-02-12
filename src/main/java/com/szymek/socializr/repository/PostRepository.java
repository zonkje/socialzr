package com.szymek.socializr.repository;

import com.szymek.socializr.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
