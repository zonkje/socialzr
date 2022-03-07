package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.model.AccessLevel;
import com.szymek.socializr.model.SocialGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocialGroupMapperTest {

    public static final String DESCRIPTION = "Test Description";
    public static final String NAME = "Test Name";
    public static final String ACCESS_LEVEL = "PUBLIC";
    SocialGroupMapper socialGroupMapper = SocialGroupMapper.INSTANCE;

    @Test
    void toSocialGroup() {

        //given
        SocialGroupDTO socialGroupDTO = new SocialGroupDTO();
        socialGroupDTO.setId(1L);
        socialGroupDTO.setDescription(DESCRIPTION);
        socialGroupDTO.setName(NAME);
        socialGroupDTO.setCreatorId(2L);
        socialGroupDTO.setAccessLevel(ACCESS_LEVEL);

        //when
        SocialGroup socialGroup = socialGroupMapper.toEntity(socialGroupDTO);

        //then
        assertEquals(Long.valueOf(1L), socialGroup.getId());
        assertEquals(DESCRIPTION, socialGroup.getDescription());
        assertEquals(NAME, socialGroup.getName());
        assertEquals(Long.valueOf(2L), socialGroup.getCreator().getId());
        assertEquals(AccessLevel.PUBLIC, socialGroup.getAccessLevel());

    }

    @Test
    void toSocialGroupDTO() {

        //given
        SocialGroup socialGroup = SocialGroup
                .builder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .accessLevel(AccessLevel.PUBLIC)
                .build();

        //when
        SocialGroupDTO socialGroupDTO = socialGroupMapper.toDTO(socialGroup);

        //then
        assertEquals(1L, socialGroupDTO.getId());
        assertEquals(NAME, socialGroupDTO.getName());
        assertEquals(DESCRIPTION, socialGroupDTO.getDescription());
        assertEquals(ACCESS_LEVEL, socialGroupDTO.getAccessLevel());

    }
}