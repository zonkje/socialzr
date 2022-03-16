package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ViolationReportDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.ViolationReportMapper;
import com.szymek.socializr.repository.UserRepository;
import com.szymek.socializr.repository.ViolationReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationReportServiceImpl implements ViolationReportService {

    private final ViolationReportRepository violationReportRepository;
    private final ViolationReportMapper violationReportMapper;
    private final UserRepository userRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @Override
    public ApplicationResponse reportUser(ViolationReportDTO violationReportDTO) {
        if (userRepository.findById(violationReportDTO.getReportedUserId()).isEmpty()) {
            throw new ResourceNotFoundException("User", "ID", violationReportDTO.getReportedUserId());
        } else {
            violationReportRepository.save(violationReportMapper.toEntity(violationReportDTO));

            return ApplicationResponse
                    .builder()
                    .messages(List.of("User with id " + violationReportDTO.getReportedUserId() + " reported"))
                    .timeStamp(formatter.format(Instant.now()))
                    .build();
        }
    }
}
