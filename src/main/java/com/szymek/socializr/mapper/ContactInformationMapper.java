package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.model.ContactInformation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactInformationMapper extends BaseMapper<ContactInformation, ContactInformationDTO> {

    ContactInformationMapper INSTANCE = Mappers.getMapper(ContactInformationMapper.class);

    @Override
    ContactInformation toEntity(ContactInformationDTO dto);

    @Override
    ContactInformationDTO toDTO(ContactInformation entity);
}
