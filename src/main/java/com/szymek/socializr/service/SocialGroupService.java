package com.szymek.socializr.service;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.model.SocialGroup;

public interface SocialGroupService extends CrudService<SocialGroupDTO, Long> {

    void checkSocialGroupPermission(Long socialGroupId, String username);

    SocialGroup findSocialGroupById(Long socialGroupId);
}
