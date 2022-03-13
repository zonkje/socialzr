package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostLabel;
import com.szymek.socializr.repository.PostLabelRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PostLabelMapper.class})
public interface PostMapper extends BeanMapper<Post, PostDTO> {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(ignore = true, target = "comments")
    @Mapping(ignore = true, target = "postLabels")
    Post toEntity(PostDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(ignore = false, target = "postLabels")
    PostDTO toDTO(Post entity);

//    Collection<PostLabel> map(List<String> value);
//
//    List<String> map(Collection<PostLabel> value);
//
//    default PostLabel map(String value){
//
//        return PostLabel.builder().name(value).build();
//    }
//
//    default String map(PostLabel value) {
//
//        return value.getName();
//    }
}