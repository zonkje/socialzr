package com.szymek.socializr.repository;

import com.szymek.socializr.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    Collection<Post> findAll();

}
