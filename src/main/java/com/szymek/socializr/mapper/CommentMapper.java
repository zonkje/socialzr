package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.model.Comment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "postId", target = "post.id")
    Comment toComment(CommentDTO commentDTO);

    @InheritInverseConfiguration(name = "toComment")
    CommentDTO toCommentDTO(Comment comment);

}
