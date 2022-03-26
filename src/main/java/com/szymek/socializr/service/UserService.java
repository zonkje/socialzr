package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.security.SignUpRequest;

import java.util.Collection;

public interface UserService {

    Collection<UserDTO> findAll(Integer page, Integer size);
    UserDTO findById(Long id);
    UserDTO create(SignUpRequest object);
    ApplicationResponse deleteById(Long id);
    UserDTO update(UserDTO object, Long id);

    ApplicationResponse joinGroup(Long userId, Long socialGroupId);
    ApplicationResponse leaveGroup(Long userId, Long socialGroupId);

}
