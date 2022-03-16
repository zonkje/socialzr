package com.szymek.socializr.repository;

import com.szymek.socializr.model.CommentThumbUp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentThumbUpRepository extends CrudRepository<CommentThumbUp, Long> {
}
