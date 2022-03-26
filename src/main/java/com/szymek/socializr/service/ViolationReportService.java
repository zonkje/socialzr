package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ViolationReportDTO;

import java.util.Collection;

public interface ViolationReportService {

    ApplicationResponse reportUser(ViolationReportDTO violationReportDTO);
    Collection<ViolationReportDTO> findAll(Integer page, Integer size);

}
