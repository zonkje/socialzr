package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.model.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    public static final String TEXT = "Test text";
    PostMapper postMapper = PostMapper.INSTANCE;

    @Test
    void toPostDTO() {
        //given
        Post post = new Post();
        post.setText(TEXT);
        post.setId(1L);

        //when
        PostDTO postDTO = postMapper.toPostDTO(post);

        //then
        assertEquals(Long.valueOf(1L), postDTO.getId());
        assertEquals(TEXT, postDTO.getText());
    }

    @Test
    void toPost() {
        //given
        PostDTO postDTO = new PostDTO();
        postDTO.setAuthorId(2L);
        postDTO.setText(TEXT);
        postDTO.setId(1L);

        //when
        Post post = postMapper.toPost(postDTO);

        //then
        assertEquals(Long.valueOf(1L), post.getId());
        assertEquals(TEXT, post.getText());
        assertEquals(Long.valueOf(2L), post.getAuthor().getId());
    }
}