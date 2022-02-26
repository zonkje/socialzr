package com.szymek.socializr.service;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.PostMapper;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Collection<Post> findAllByAuthor() {
        return null;
    }

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
    public void delete(PostDTO postDTO) {
        Post post = postMapper.toPost(postDTO);
        postRepository.delete(post);
    }

    @Override
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }
}
