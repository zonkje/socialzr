package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.model.Address;
import com.szymek.socializr.model.ContactInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactInformationMapperTest {

    public static final String EMAIL = "email@test.com";
    public static final String PHONE_NUMBER = "123456789";
    public static final String ADDRESS = "Test Address";
    public static final String CITY = "Test City";
    public static final String COUNTRY = "Test Country";
    public static final String STATE = "Test State";
    ContactInformationMapper contactInformationMapper = ContactInformationMapper.INSTANCE;

    @Test
    void toContactInformation() {

        //given
        ContactInformationDTO contactInformationDTO = new ContactInformationDTO();
        contactInformationDTO.setId(1L);
        contactInformationDTO.setEmail(EMAIL);
        contactInformationDTO.setPhoneNumber(PHONE_NUMBER);
        contactInformationDTO.setAddress(Address.builder()
                .address(ADDRESS)
                .city(CITY)
                .country(COUNTRY)
                .state(STATE)
                .build()
        );

        //when
        ContactInformation contactInformation = contactInformationMapper.toEntity(contactInformationDTO);

        //then
        assertEquals(Long.valueOf(1L), contactInformation.getId());
        assertEquals(EMAIL, contactInformation.getEmail());
        assertEquals(PHONE_NUMBER, contactInformation.getPhoneNumber());
        assertEquals(ADDRESS, contactInformation.getAddress().getAddress());
        assertEquals(CITY, contactInformation.getAddress().getCity());
        assertEquals(COUNTRY, contactInformation.getAddress().getCountry());
        assertEquals(STATE, contactInformation.getAddress().getState());

    }

    @Test
    void toContactInformationDTO() {

        //given
        ContactInformation contactInformation = ContactInformation
                .builder()
                .id(1L)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .address(Address
                        .builder()
                        .address(ADDRESS)
                        .country(COUNTRY)
                        .city(CITY)
                        .state(STATE)
                        .build()
                )
                .build();

        //when
        ContactInformationDTO contactInformationDTO = contactInformationMapper.toDTO(contactInformation);

        //then
        assertEquals(Long.valueOf(1L), contactInformationDTO.getId());
        assertEquals(EMAIL, contactInformationDTO.getEmail());
        assertEquals(PHONE_NUMBER, contactInformationDTO.getPhoneNumber());
        assertEquals(ADDRESS, contactInformationDTO.getAddress().getAddress());
        assertEquals(CITY, contactInformationDTO.getAddress().getCity());
        assertEquals(COUNTRY, contactInformationDTO.getAddress().getCountry());
        assertEquals(STATE, contactInformationDTO.getAddress().getState());

    }
}