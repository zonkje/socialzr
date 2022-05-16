package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.model.User;
import com.szymek.socializr.security.EncodedMapping;
import com.szymek.socializr.security.PasswordEncoderMapper;
import com.szymek.socializr.security.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static com.szymek.socializr.common.SocialzrConstants.DEFAULT_AVATAR_URL;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(ignore = true, target = "posts")
    @Mapping(ignore = true, target = "socialGroups")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "avatarUrl", source = "avatarUrl",
            defaultValue = DEFAULT_AVATAR_URL)
    @Mapping(target = "role", expression = "java(com.szymek.socializr.model.Role.USER)")
    User toEntity(SignUpRequest signUpRequest);


    @Mapping(target = "contactInformationId", source = "contactInformation.id")
    UserDTO toDTO(User entity);

}
