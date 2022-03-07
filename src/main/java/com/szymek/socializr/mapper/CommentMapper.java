package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.model.Comment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BeanMapper<Comment, CommentDTO> {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "postId", target = "post.id")
    Comment toEntity(CommentDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    CommentDTO toDTO(Comment entity);
}
