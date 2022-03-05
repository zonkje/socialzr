package com.szymek.socializr.repository;

import com.szymek.socializr.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Collection<Comment> findAll();
    Collection<Comment> findCommentsByPostId(Long postId);

}
