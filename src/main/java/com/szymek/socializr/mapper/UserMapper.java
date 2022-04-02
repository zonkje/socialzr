package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.model.User;
import com.szymek.socializr.security.EncodedMapping;
import com.szymek.socializr.security.PasswordEncoderMapper;
import com.szymek.socializr.security.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "contactInformation.id", source = "contactInformationId")
    @Mapping(ignore = true, target = "posts")
    @Mapping(ignore = true, target = "socialGroups")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "role", expression = "java(com.szymek.socializr.model.Role.USER)")
    User toEntity(SignUpRequest signUpRequest);


    @Mapping(target = "contactInformationId", source = "contactInformation.id")
    UserDTO toDTO(User entity);

}
