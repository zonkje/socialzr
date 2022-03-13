package com.szymek.socializr.repository;

import com.szymek.socializr.model.PostLabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLabelRepository extends CrudRepository<PostLabel, Long> {
}
