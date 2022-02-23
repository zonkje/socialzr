package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.model.SocialGroup;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SocialGroupMapper {

    SocialGroupMapper INSTANCE = Mappers.getMapper(SocialGroupMapper.class);

    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(ignore = true, target = "members")
    SocialGroup toSocialGroup(SocialGroupDTO socialGroupDTO);

    @InheritInverseConfiguration(name = "toSocialGroup")
    SocialGroupDTO toSocialGroupDTO(SocialGroup socialGroup);

}
