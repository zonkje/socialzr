package com.szymek.socializr.service;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.CommentMapper;
import com.szymek.socializr.mapper.PostMapper;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.CommentRepository;
import com.szymek.socializr.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @Override
    public Collection<PostDTO> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findById(Long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        return postMapper.toPostDTO(post);
    }

    @Override
    public PostDTO create(PostDTO postDTO) {
        Post post = postMapper.toPost(postDTO);
        return postMapper.toPostDTO(postRepository.save(post));
    }

    @Override
    public PostDTO update(PostDTO postToUpdate, Long postId) {
        return postRepository
                .findById(postId)
                .map(post -> {
                            if (post.getText() != null) {
                                post.setText(postToUpdate.getText());
                            }
                            return postMapper.toPostDTO(postRepository.save(post));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Collection<CommentDTO> findAllPostComments(Long postId) {
        return commentRepository
                .findCommentsByPostId(postId)
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
