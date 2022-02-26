package com.szymek.socializr.service;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.CommentMapper;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    //TODO -remove useless methods
    @Override
    public Collection<CommentDTO> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findById(Long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentDTO create(CommentDTO commentDTO) {
        Comment comment = commentMapper.toComment(commentDTO);
        return commentMapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void delete(CommentDTO object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
