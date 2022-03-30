package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.ThumbUpException;
import com.szymek.socializr.mapper.PostLabelMapper;
import com.szymek.socializr.mapper.PostMapper;
import com.szymek.socializr.mapper.PostThumbUpMapper;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostThumbUp;
import com.szymek.socializr.repository.PostRepository;
import com.szymek.socializr.repository.PostThumbUpRepository;
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
    private final PostMapper postMapper;
    private final PostLabelMapper postLabelMapper;
    private final PostThumbUpRepository postThumbUpRepository;
    private final PostThumbUpMapper postThumbUpMapper;
    private final UserService userService;

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
    public Collection<PostDTO> findAllByAuthor(Long authorId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Post> posts = postRepository.findPostsByAuthorId(authorId, pageable);
        List<Post> postsList = posts.getContent();
        return postsList
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findById(Long postId) {
        Post post = findPostById(postId);
        return postMapper.toDTO(post);
    }

    //TODO: -use @AfterMapping/@BeforeMapping in postMapper instead of saving post twice
    @Override
    public PostDTO create(PostDTO postDTO, String authorName) {
        postDTO.setAuthorId(userService.findByUsername(authorName).getId());
        Post post = postRepository.save(postMapper.toEntity(postDTO));
        post.setPostLabels(postLabelMapper.map(postDTO.getPostLabels(), post));
        return postMapper.toDTO(postRepository.save(post));
    }

    //TODO: -move entity updating logic to mappers
    @Override
    public PostDTO update(PostDTO postToUpdate, String loggedUserName) {
        userService.checkPermission(postToUpdate.getAuthorId(), loggedUserName, "edit", "post");
        Long postId = postToUpdate.getId();
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
    public ApplicationResponse deleteById(Long postId, String loggedUserName) {
        Post postToDelete = findPostById(postId);
        userService.checkPermission(postToDelete.getAuthor().getId(), loggedUserName, "delete",
                "post");
        String message = String.format("Post with ID: %s has been deleted", postId);
        postRepository.deleteById(postId);
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public Collection<PostDTO> findAllByLabelId(Long labelId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Post> posts = postRepository.findPostsByPostLabelsId(labelId, pageable);
        List<Post> postList = posts.getContent();
        return postList
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PostDTO> findAllByLabelName(String labelName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<Post> posts = postRepository.findPostsByPostLabelsName(labelName, pageable);
        List<Post> postList = posts.getContent();
        return postList
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostThumbUpDTO addThumbUpToPost(PostThumbUpDTO postThumbUpDTO, String authorName) {
        Long postId = postThumbUpDTO.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        boolean isAlreadyThumbUpByUser = post.getPostThumbUps().stream()
                .anyMatch(
                        postThumbUp -> postThumbUp.getAuthor().getId().equals(postThumbUpDTO.getAuthorId())
                );

        if (!isAlreadyThumbUpByUser) {
            postThumbUpDTO.setAuthorId(userService.findByUsername(authorName).getId());
            PostThumbUp postThumbUp = postThumbUpMapper.toEntity(postThumbUpDTO);
            return postThumbUpMapper.toDTO(postThumbUpRepository.save(postThumbUp));
        } else {
            throw new ThumbUpException(postThumbUpDTO.getPostId(), postThumbUpDTO.getAuthorId());
        }

    }

    @Override
    public ApplicationResponse deletePostThumbUpById(Long thumbUpId, String loggedUserName) {
        PostThumbUp postThumbUpToDelete = postThumbUpRepository.findById(thumbUpId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Thumb Up", "ID", thumbUpId));
        userService.checkPermission(postThumbUpToDelete.getAuthor().getId(), loggedUserName, "delete",
                "post thumb up");
        String message = String.format("Post Thumb Up with ID: %s has been deleted", thumbUpId);
        postThumbUpRepository.deleteById(thumbUpId);

        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    private Post findPostById(Long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

}
