package com.szymek.socializr.service;

import com.szymek.socializr.model.Comment;
import com.szymek.socializr.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Collection<Comment> findAll() {
        return (Collection<Comment>) commentRepository.findAll();
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
