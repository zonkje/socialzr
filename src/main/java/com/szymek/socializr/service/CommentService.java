package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.CommentThumbUpDTO;

import java.util.Collection;

public interface CommentService extends CrudService<CommentDTO, Long>{

    CommentThumbUpDTO addThumbUpToComment(CommentThumbUpDTO commentThumbUpDTO, String authorName);

    ApplicationResponse deleteCommentThumbUpById(Long thumbUpId, String loggedUserName);

    Collection<CommentDTO> findAllPostComments(Long postId, Integer page, Integer size);

    Collection<CommentDTO> findAllByUser(String username, Integer page, Integer size);
}
