package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.dto.SocialGroupPostDTO;

import java.util.Collection;

public interface SocialGroupPostService extends CrudService<SocialGroupPostDTO, Long> {

    Collection<SocialGroupPostDTO> findAllByLabelId(Long labelId, String loggedUserName, Integer page, Integer size);

    Collection<SocialGroupPostDTO> findAllByLabelName(String labelName, String loggedUserName, Integer page, Integer size);

    PostThumbUpDTO addThumbUpToPost(PostThumbUpDTO postThumbUp, String authorName);

    ApplicationResponse deletePostThumbUpById(Long thumbUpId, String loggedUserName);

    Collection<SocialGroupPostDTO> findAllBySocialGroupId(Long socialGroupId, String loggedUserName, Integer page, Integer size);

}
