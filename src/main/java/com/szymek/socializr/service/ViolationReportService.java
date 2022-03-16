package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ViolationReportDTO;

public interface ViolationReportService {

    ApplicationResponse reportUser(ViolationReportDTO violationReportDTO);

}
