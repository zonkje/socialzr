package com.szymek.socializr.service;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.dto.UserDTO;

import java.util.Collection;

public interface SocialGroupService extends CrudService<SocialGroupDTO, Long> {

    Collection<UserDTO> findAllMembers(Long socialGroupId);

}
