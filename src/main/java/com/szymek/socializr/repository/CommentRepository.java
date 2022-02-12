package com.szymek.socializr.repository;

import com.szymek.socializr.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
