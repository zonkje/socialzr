package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.model.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    public static final String TEXT = "Test text";
    CommentMapper commentMapper = CommentMapper.INSTANCE;

    @Test
    void toComment() {

        //given
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setText(TEXT);
        commentDTO.setAuthorId(2L);
        commentDTO.setPostId(3L);

        //when
        Comment comment = commentMapper.toEntity(commentDTO);

        //then
        assertEquals(1L, comment.getId());
        assertEquals(TEXT, comment.getText());
        assertEquals(2L, comment.getAuthor().getId());
        assertEquals(3L, comment.getPost().getId());

    }

    @Test
    void toCommentDTO() {

        //given
        Comment comment = Comment.builder().id(1L).text(TEXT).build();
        comment.setId(1L);
        comment.setText(TEXT);

        //when
        CommentDTO commentDTO = commentMapper.toDTO(comment);

        //then
        assertEquals(Long.valueOf(1L), commentDTO.getId());
        assertEquals(TEXT, commentDTO.getText());

    }
}