package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.CommentMapper;
import com.szymek.socializr.mapper.PostMapper;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.CommentRepository;
import com.szymek.socializr.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @Override
    public Collection<PostDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postsList = posts.getContent();
        return postsList
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findById(Long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        return postMapper.toDTO(post);
    }

    @Override
    public PostDTO create(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        return postMapper.toDTO(postRepository.save(post));
    }

    @Override
    public PostDTO update(PostDTO postToUpdate, Long postId) {
        return postRepository
                .findById(postId)
                .map(post -> {
                            if (post.getText() != null) {
                                post.setText(postToUpdate.getText());
                            }
                            return postMapper.toDTO(postRepository.save(post));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public ApplicationResponse deleteById(Long postId) {
        String message;
        if (postRepository.findById(postId).isPresent()) {
            message = String.format("Post with ID: %s has been deleted", postId);
            commentRepository.deleteById(postId);
        } else {
            message = String.format("Post with ID: %s doesn't exist", postId);
        }
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
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
}
