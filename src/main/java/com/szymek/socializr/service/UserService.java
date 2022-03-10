package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.UserDTO;

public interface UserService extends CrudService<UserDTO, Long>{

    ApplicationResponse joinGroup(Long userId, Long socialGroupId);
    ApplicationResponse leaveGroup(Long userId, Long socialGroupId);

}
