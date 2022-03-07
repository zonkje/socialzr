package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends BeanMapper<User, UserDTO> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    @Mapping(source = "contactInformationId", target = "contactInformation.id")
    @Mapping(ignore = true, target = "posts")
    @Mapping(ignore = true, target = "socialGroups")
    User toEntity(UserDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    UserDTO toDTO(User entity);
}
