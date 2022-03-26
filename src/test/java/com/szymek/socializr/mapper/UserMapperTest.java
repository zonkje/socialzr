package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.model.User;
import com.szymek.socializr.security.SignUpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

class UserMapperTest {

    public static final String FIRST_NAME = "Test First Name";
    public static final String LAST_NAME = "Test Last Name";
    UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    void toUser() {

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setContactInformationId(2L);

        //when
        User user = userMapper.toEntity((SignUpRequest) userDTO);

        //then
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(Long.valueOf(2L), user.getContactInformation().getId());

    }

    @Test
    void toUserDTO() {

        //given
        User user = User
                .builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        //when
        UserDTO userDTO = userMapper.toDTO(user);

        //then
        assertEquals(Long.valueOf(1L), userDTO.getId());
        assertEquals(FIRST_NAME, userDTO.getFirstName());
        assertEquals(LAST_NAME, userDTO.getLastName());
        assertNull("contactInformationId should be null", userDTO.getContactInformationId());

    }
}