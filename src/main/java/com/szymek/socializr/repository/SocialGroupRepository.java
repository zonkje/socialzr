package com.szymek.socializr.repository;

import com.szymek.socializr.model.SocialGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialGroupRepository extends CrudRepository<SocialGroup, Long> {
}
