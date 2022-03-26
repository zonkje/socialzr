package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.model.PostThumbUp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = {PostLabelMapper.class})
public interface PostMapper extends BaseMapper<Post, PostDTO> {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(ignore = true, target = "comments")
    @Mapping(ignore = true, target = "postLabels")
    @Mapping(ignore = true, target = "postThumbUps")
    Post toEntity(PostDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(ignore = false, target = "postLabels")
    @Mapping(source = "postThumbUps", target = "postThumbUpCount")
    PostDTO toDTO(Post entity);

    default Integer postThumbUpCountMap(Collection<PostThumbUp> postThumbUps){
        if(postThumbUps == null) return 0;
        return postThumbUps.size();
    }

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