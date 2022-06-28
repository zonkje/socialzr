package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.dto.PostThumbUpDTO;

import java.util.Collection;

public interface PostService extends CrudService<PostDTO, Long> {

    Collection<PostDTO> findAllByLabelId(Long labelId, Integer page, Integer size);

    Collection<PostDTO> findAllByLabelName(String labelName, Integer page, Integer size);

    PostThumbUpDTO addThumbUpToPost(PostThumbUpDTO postThumbUp, String authorName);

    ApplicationResponse deletePostThumbUpByPostId(Long thumbUpId, String loggedUserName);

    Collection<PostDTO> findAllByAuthor(Long authorId, Integer page, Integer size);
}
