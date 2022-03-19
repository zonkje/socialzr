package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.CommentThumbUpDTO;

import java.util.Collection;

public interface CommentService extends CrudService<CommentDTO, Long>{

    CommentThumbUpDTO addThumbUpToComment(CommentThumbUpDTO commentThumbUpDTO);

    ApplicationResponse deleteCommentThumbUpById(Long thumbUpId);

    Collection<CommentDTO> findAllPostComments(Long postId, Integer page, Integer size);
}
