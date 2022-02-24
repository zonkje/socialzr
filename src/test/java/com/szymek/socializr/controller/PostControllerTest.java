package com.szymek.socializr.controller;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    public static final String TEXT = "Test text";

    @Mock
    PostService postService;

    @InjectMocks
    PostController postController;

    MockMvc mockMvc;

    Post post;

    @BeforeEach
    void setUp() {

        post = Post.builder()
                .id(1L)
                .text(TEXT)
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void getPost() throws Exception {

        given(postService.findById(any())).willReturn(Optional.of(post));

        mockMvc.perform(get("/post/" + post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id" , is(post.getId().intValue())))
                .andExpect(jsonPath("$.text", is(post.getText())));
    }

    @Test
    @DisplayName("Should list all posts when making GET request to endpoint /post")
    void getAllPosts() throws Exception {
        Post post1 = Post.builder().id(1L).text(TEXT).build();
        Post post2 = Post.builder().id(2L).text(TEXT+"2").build();

        Mockito.when(postService.findAll()).thenReturn(asList(post1, post2));

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[0].text", Matchers.is(TEXT)))
                .andExpect(jsonPath("$[1].text", Matchers.is(TEXT+"2")));
    }

}