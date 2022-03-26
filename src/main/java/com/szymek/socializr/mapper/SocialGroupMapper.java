package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.model.SocialGroup;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SocialGroupMapper extends BaseMapper<SocialGroup, SocialGroupDTO> {

    SocialGroupMapper INSTANCE = Mappers.getMapper(SocialGroupMapper.class);

    @Override
    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(ignore = true, target = "members")
    SocialGroup toEntity(SocialGroupDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    SocialGroupDTO toDTO(SocialGroup entity);
}
