package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.CommentThumbUpDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.ThumbUpException;
import com.szymek.socializr.mapper.CommentMapper;
import com.szymek.socializr.mapper.CommentThumbUpMapper;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.model.CommentThumbUp;
import com.szymek.socializr.repository.CommentRepository;
import com.szymek.socializr.repository.CommentThumbUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentThumbUpMapper commentThumbUpMapper;
    private final CommentThumbUpRepository commentThumbUpRepository;
    private final UserService userService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

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
    public Collection<CommentDTO> findAllByUser(String username, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Comment> comments = commentRepository.findCommentsByAuthorUsername(username, pageable);
        List<Comment> commentsList = comments.getContent();
        return commentsList
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findById(Long commentId) {
        Comment comment = findCommentById(commentId);
        return commentMapper.toDTO(comment);
    }

    @Override
    public Collection<CommentDTO> findAllPostComments(Long postId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Comment> comments = commentRepository.findCommentsByPostId(postId, pageable);
        List<Comment> commentsList = comments.getContent();
        return commentsList
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO create(CommentDTO commentDTO, String authorName) {
        commentDTO.setAuthorId(userService.findByUsername(authorName).getId());
        Comment comment = commentMapper.toEntity(commentDTO);
        return commentMapper.toDTO(commentRepository.save(comment));
    }

    @Override
    public ApplicationResponse deleteById(Long commentId, String loggedUserName) {
        Comment commentToDelete = findCommentById(commentId);
        userService.checkPermission(commentToDelete.getAuthor().getId(), loggedUserName, "delete",
                "comment");
        String message = String.format("Comment with ID: %s has been deleted", commentId);
        commentRepository.deleteById(commentId);

        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public CommentDTO update(CommentDTO commentToUpdate, String loggedUserName) {
        userService.checkPermission(commentToUpdate.getAuthorId(), loggedUserName, "edit", "comment");
        Long commentId = commentToUpdate.getId();
        return commentRepository    //replace with private method
                .findById(commentId)
                .map(comment -> {
                            if (commentToUpdate.getText() != null) {
                                comment.setText(commentToUpdate.getText());
                            }
                            return commentMapper.toDTO(commentRepository.save(comment));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
    }

    @Override
    public CommentThumbUpDTO addThumbUpToComment(CommentThumbUpDTO commentThumbUpDTO, String authorName) {
        Long commentId = commentThumbUpDTO.getCommentId();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
        commentThumbUpDTO.setAuthorId(userService.findByUsername(authorName).getId());

        boolean isAlreadyThumbUpByUser = comment.getCommentThumbUps().stream()
                .anyMatch(
                        commentThumbUp -> commentThumbUp.getAuthor().getId().equals(commentThumbUpDTO.getAuthorId())
                );

        if (!isAlreadyThumbUpByUser) {
            CommentThumbUp commentThumbUp = commentThumbUpMapper.toEntity(commentThumbUpDTO);
            return commentThumbUpMapper.toDTO(commentThumbUpRepository.save(commentThumbUp));
        } else {
            throw new ThumbUpException(commentThumbUpDTO.getCommentId(), commentThumbUpDTO.getAuthorId());
        }
    }

    @Override
    public ApplicationResponse deleteCommentThumbUpByCommentId(Long commentId, String loggedUserName) {
        Long authorId = userService.findUserByUsername(loggedUserName).getId();
        CommentThumbUp commentThumbUpToDelete = commentThumbUpRepository.findByAuthorIdAndCommentId(authorId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
        userService.checkPermission(commentThumbUpToDelete.getAuthor().getId(), loggedUserName, "delete",
                "comment thumb up");
        String message = String.format("Comment Thumb Up with ID: %s has been deleted", commentThumbUpToDelete.getId());
        commentThumbUpRepository.deleteById(commentThumbUpToDelete.getId());

        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
    }

}
