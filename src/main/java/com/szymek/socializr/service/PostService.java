package com.szymek.socializr.service;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.PostDTO;

import java.util.Collection;

public interface PostService extends CrudService<PostDTO, Long>{

    Collection<CommentDTO> findAllPostComments(Long postId);

}
