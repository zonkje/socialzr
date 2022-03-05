package com.szymek.socializr.repository;

import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Collection<User> findAll();
    Collection<User> findUsersBySocialGroups(SocialGroup socialGroup);

}
