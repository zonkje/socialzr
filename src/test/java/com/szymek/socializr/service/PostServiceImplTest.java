package com.szymek.socializr.service;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(postRepository);
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
}