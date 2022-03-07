package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper extends BeanMapper<Post, PostDTO> {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(ignore = true, target = "comments")
    Post toEntity(PostDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    PostDTO toDTO(Post entity);
}