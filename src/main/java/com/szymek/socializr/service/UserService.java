package com.szymek.socializr.service;

import com.szymek.socializr.dto.UserDTO;

public interface UserService extends CrudService<UserDTO, Long>{

    void joinGroup(Long userId, Long socialGroupId);
    void leaveGroup(Long userId, Long socialGroupId);

}
