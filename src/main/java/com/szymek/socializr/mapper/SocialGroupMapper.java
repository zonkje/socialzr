package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.model.SocialGroup;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static com.szymek.socializr.common.SocialzrConstants.DEFAULT_SOCIAL_GROUP_AVATAR_URL;

@Mapper(componentModel = "spring")
public interface SocialGroupMapper extends BaseMapper<SocialGroup, SocialGroupDTO> {


    SocialGroupMapper INSTANCE = Mappers.getMapper(SocialGroupMapper.class);

    @Override
    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(ignore = true, target = "members")
    @Mapping(target = "avatarUrl", source = "avatarUrl",
            defaultValue = DEFAULT_SOCIAL_GROUP_AVATAR_URL)
    SocialGroup toEntity(SocialGroupDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    SocialGroupDTO toDTO(SocialGroup entity);
}
