package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.exception.UnauthorizedException;
import com.szymek.socializr.model.User;
import com.szymek.socializr.security.SignUpRequest;

import java.util.Collection;

public interface UserService {

    Collection<UserDTO> findAll(Integer page, Integer size);

    UserDTO findById(Long id);

    UserDTO create(SignUpRequest object);

    ApplicationResponse deleteById(Long id);

    UserDTO update(UserDTO object, String loggedUserName);

    UserDTO findByUsername(String username);

    ApplicationResponse joinGroup(String loggedUserName, Long socialGroupId);

    ApplicationResponse leaveGroup(String loggedUserName, Long socialGroupId);

    User findUserByUsername(String username);

    Collection<UserDTO> findAllBySocialGroupId(Long socialGroupId, String loggedUserName, Integer page, Integer size);

    void checkPermission(Long objectId, String loggedUserName, String operation, String entityName)
            throws UnauthorizedException;
}
