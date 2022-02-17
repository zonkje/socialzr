package com.szymek.socializr.service;

import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return (Collection<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
