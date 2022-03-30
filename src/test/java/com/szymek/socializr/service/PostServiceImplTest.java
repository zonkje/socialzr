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
import org.springframework.data.domain.*;

import java.util.*;

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
        returnPostDTO.setAuthorId(1L);
//        postMapper = PostMapper.INSTANCE;
    }

    @Test
    @Disabled
    void findAll() {
        Post post = new Post();
        List<Post> postData = new ArrayList<>();
        postData.add(post);

        when(postRepository.findAll()).thenReturn(postData);

        Collection<PostDTO> posts = postService.findAll(
                1,30
        );
        assertEquals(posts.size(), 1);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @Disabled
    void testFindAll() {
        List<Post> returnPostsList = new ArrayList<>();
        returnPostsList.add(Post.builder().id(1L).build());
        returnPostsList.add(Post.builder().id(2L).build());
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createDate");
        Page<Post> returnPostPage = new PageImpl<>(returnPostsList);

        lenient().when(postRepository.findAll(pageable)).thenReturn(returnPostPage);

        Collection<PostDTO> posts = postService.findAll(0, 30);
//        posts.forEach(post -> System.out.println(post));
//        System.out.println(posts.size());
        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    //TODO: -mapper returning null, find out why
    @Test
    @Disabled
    void findById() {
//        System.out.println(returnPostDTO);
        Post post = postMapper.toEntity(returnPostDTO);
//        System.out.println(post);
        Optional<Post> optionalPost = Optional.of(post);
        when(postRepository.findById(anyLong())).thenReturn(optionalPost);
//        System.out.println(optionalPost);
        PostDTO postDTO = postService.findById(1L);
//        System.out.println(postDTO);
        assertNotNull(postDTO);
    }

    @Test
    @Disabled
    void create() {
        PostDTO postDTOToSave = new PostDTO();
        postDTOToSave.setId(1L);
        Post post = postMapper.toEntity(postDTOToSave);

        when(postRepository.save(any())).thenReturn(post);

        PostDTO savedPost = postService.create(postDTOToSave, anyString());

        assertNotNull(savedPost);
    }

    @Test
    void deleteById() {
        postService.deleteById(1L, anyString());

        verify(postRepository).deleteById(anyLong());
    }
}