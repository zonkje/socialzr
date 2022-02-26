package com.szymek.socializr.repository;

import com.szymek.socializr.model.SocialGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SocialGroupRepository extends CrudRepository<SocialGroup, Long> {

    Collection<SocialGroup> findAll();

}
