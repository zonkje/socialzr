package com.szymek.socializr.repository;

import com.szymek.socializr.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Collection<Comment> findAll();
    Page<Comment> findCommentsByPostId(Long postId, Pageable pageable);

}
