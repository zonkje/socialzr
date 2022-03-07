package com.szymek.socializr.service;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.CommentMapper;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    //TODO -remove useless methods
    @Override
    public Collection<CommentDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<Comment> commentsList = comments.getContent();
        return commentsList
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findById(Long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
        return commentMapper.toDTO(comment);
    }

    @Override
    public CommentDTO create(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        return commentMapper.toDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO update(CommentDTO commentToUpdate, Long commentId) {
        return commentRepository
                .findById(commentId)
                .map(comment -> {
                            if (comment.getText() != null) {
                                comment.setText(commentToUpdate.getText());
                            }
                            return commentMapper.toDTO(commentRepository.save(comment));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
    }

}
