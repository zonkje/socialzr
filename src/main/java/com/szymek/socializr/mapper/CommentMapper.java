package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.model.CommentThumbUp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDTO> {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "postId", target = "post.id")
    Comment toEntity(CommentDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "commentThumbUps", target = "commentThumbUpCount")
    CommentDTO toDTO(Comment entity);

    default Integer commentThumbUpCountMap(Collection<CommentThumbUp> commentThumbUps) {
        if (commentThumbUps == null) return 0;
        return commentThumbUps.size();
    }
}
