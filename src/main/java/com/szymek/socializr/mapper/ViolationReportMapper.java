package com.szymek.socializr.mapper;

import com.szymek.socializr.dto.ViolationReportDTO;
import com.szymek.socializr.model.ViolationReport;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViolationReportMapper extends BeanMapper<ViolationReport, ViolationReportDTO> {

    @Override
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "reportedUserId", target = "reportedUser.id")
    ViolationReport toEntity(ViolationReportDTO dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    ViolationReportDTO toDTO(ViolationReport entity);
}
