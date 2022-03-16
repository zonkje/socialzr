package com.szymek.socializr.repository;

import com.szymek.socializr.model.PostThumbUp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostThumbUpRepository extends CrudRepository<PostThumbUp, Long> {
}
