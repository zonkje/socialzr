package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.CommentThumbUpDTO;
import com.szymek.socializr.model.CommentThumbUp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentThumbUpMapper extends BaseMapper<CommentThumbUp, CommentThumbUpDTO> {

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "commentId", target = "comment.id")
    CommentThumbUp toEntity(CommentThumbUpDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    CommentThumbUpDTO toDTO(CommentThumbUp entity);
}
