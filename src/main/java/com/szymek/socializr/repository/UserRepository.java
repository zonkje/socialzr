package com.szymek.socializr.repository;

import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Collection<User> findAll();
    Page<User> findUsersBySocialGroups(SocialGroup socialGroup, Pageable pageable);

}
