package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.dto.SocialGroupPostDTO;

import java.util.Collection;

public interface SocialGroupPostService extends CrudService<SocialGroupPostDTO, Long> {

    Collection<SocialGroupPostDTO> findAllByLabelId(Long labelId, Integer page, Integer size);

    Collection<SocialGroupPostDTO> findAllByLabelName(String labelName, Integer page, Integer size);

    PostThumbUpDTO addThumbUpToPost(PostThumbUpDTO postThumbUp);

    ApplicationResponse deletePostThumbUpById(Long thumbUpId);

    Collection<SocialGroupPostDTO> findAllBySocialGroupId(Long socialGroupId, Integer page, Integer size);

}
