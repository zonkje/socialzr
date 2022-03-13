package com.szymek.socializr.repository;

import com.szymek.socializr.model.ThumbUp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbUpRepository extends CrudRepository<ThumbUp, Long> {
}
