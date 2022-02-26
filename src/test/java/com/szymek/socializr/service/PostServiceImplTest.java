package com.szymek.socializr.service;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.mapper.PostMapper;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    final String DEFAULT_TEXT = "TEST TEXT";

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    PostDTO returnPostDTO;

    @BeforeEach
    public void setUp() {
        returnPostDTO = new PostDTO();
        returnPostDTO.setId(1L);
        returnPostDTO.setText(DEFAULT_TEXT);
    }

    @Test
    void findAll() {
        Post post = new Post();
        Collection<Post> postData = new HashSet<>();
        postData.add(post);

        when(postRepository.findAll()).thenReturn(postData);

        Collection<PostDTO> posts = postService.findAll();
        assertEquals(posts.size(), 1);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testFindAll() {
        Set<Post> returnPostsSet = new HashSet<>();
        returnPostsSet.add(Post.builder().id(1L).build());
        returnPostsSet.add(Post.builder().id(2L).build());

        when(postRepository.findAll()).thenReturn(returnPostsSet);

        Collection<PostDTO> posts = postService.findAll();
//        posts.forEach(post -> System.out.println(post));
//        System.out.println(posts.size());
        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    //TODO: -mapper returning null, find out why
    @Test
    @Disabled
    void findById() {
        Post post = postMapper.toPost(returnPostDTO);
        System.out.println(post);
        Optional<Post> optionalPost = Optional.of(post);
        when(postRepository.findById(anyLong())).thenReturn(optionalPost);

        PostDTO postDTO = postService.findById(1L);

        assertNotNull(postDTO);
    }

    @Test
    @Disabled
    void create() {
        PostDTO postDTOToSave = new PostDTO();
        postDTOToSave.setId(1L);
        Post post = postMapper.toPost(postDTOToSave);

        when(postRepository.save(any())).thenReturn(post);

        PostDTO savedPost = postService.create(postDTOToSave);

        assertNotNull(savedPost);
    }

    @Test
    void delete() {
        postService.delete(returnPostDTO);

        verify(postRepository).delete(any());
    }

    @Test
    void deleteById() {
        postService.deleteById(1L);

        verify(postRepository).deleteById(anyLong());
    }
}