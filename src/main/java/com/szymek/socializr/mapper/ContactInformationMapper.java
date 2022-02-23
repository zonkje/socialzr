package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.model.ContactInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactInformationMapper {

    ContactInformationMapper INSTANCE = Mappers.getMapper(ContactInformationMapper.class);

    ContactInformation toContactInformation(ContactInformationDTO contactInformationDTO);

    ContactInformationDTO toContactInformationDTO(ContactInformation contactInformation);
}
