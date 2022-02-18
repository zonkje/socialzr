package com.szymek.socializr.service;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
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

    Post returnPost;

    @BeforeEach
    public void setUp() {
        returnPost = Post.builder().id(1L).text(DEFAULT_TEXT).build();
    }

    @Test
    void findAll() {
        Post post = new Post();
        Collection postData = new HashSet();
        postData.add(post);

        when(postRepository.findAll()).thenReturn(postData);

        Collection<Post> posts = postService.findAll();
        assertEquals(posts.size(), 1);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testFindAll() {
        Set<Post> returnPostsSet = new HashSet<>();
        returnPostsSet.add(Post.builder().id(1L).build());
        returnPostsSet.add(Post.builder().id(2L).build());

        when(postRepository.findAll()).thenReturn(returnPostsSet);

        Collection<Post> posts = postService.findAll();
        posts.forEach(post -> System.out.println(post.getId()));
        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    @Test
    void findById() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(returnPost));

        Post post = postService.findById(1L);

        assertNotNull(post);
    }

    @Test
    void create() {
        Post postToSave = Post.builder().id(1L).build();

        when(postRepository.save(any())).thenReturn(returnPost);

        Post savedPost = postService.create(postToSave);

        assertNotNull(savedPost);
    }

    @Test
    void delete() {
        postService.delete(returnPost);

        verify(postRepository).delete(any());
    }

    @Test
    void deleteById() {
        postService.deleteById(1L);

        verify(postRepository).deleteById(anyLong());
    }
}