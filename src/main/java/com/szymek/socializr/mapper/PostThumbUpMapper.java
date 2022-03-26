package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.model.PostThumbUp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostThumbUpMapper extends BaseMapper<PostThumbUp, PostThumbUpDTO> {

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "postId", target = "post.id")
    PostThumbUp toEntity(PostThumbUpDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    PostThumbUpDTO toDTO(PostThumbUp entity);
}
