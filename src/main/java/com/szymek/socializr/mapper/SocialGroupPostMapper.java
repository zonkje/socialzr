package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.SocialGroupPostDTO;
import com.szymek.socializr.model.PostThumbUp;
import com.szymek.socializr.model.SocialGroupPost;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = {PostLabelMapper.class})
public interface SocialGroupPostMapper extends BaseMapper<SocialGroupPost, SocialGroupPostDTO> {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "socialGroupId", target = "socialGroup.id")
    @Mapping(ignore = true, target = "comments")
    @Mapping(ignore = true, target = "postLabels")
    @Mapping(ignore = true, target = "postThumbUps")
    SocialGroupPost toEntity(SocialGroupPostDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(ignore = false, target = "postLabels")
    @Mapping(source = "postThumbUps", target = "postThumbUpCount")
    SocialGroupPostDTO toDTO(SocialGroupPost entity);

    default Integer postThumbUpCountMap(Collection<PostThumbUp> postThumbUps) {
        if (postThumbUps == null) return 0;
        return postThumbUps.size();
    }

}
