package com.szymek.socializr.repository;

import com.szymek.socializr.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
