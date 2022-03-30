package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ViolationReportDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.ViolationReportMapper;
import com.szymek.socializr.model.ViolationReport;
import com.szymek.socializr.repository.ViolationReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViolationReportServiceImpl implements ViolationReportService {

    private final ViolationReportRepository violationReportRepository;
    private final ViolationReportMapper violationReportMapper;
    private final UserService userService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @Override
    public ApplicationResponse reportUser(ViolationReportDTO violationReportDTO, String authorName) {
        if (userService.findById(violationReportDTO.getReportedUserId()) == null) {
            throw new ResourceNotFoundException("User", "ID", violationReportDTO.getReportedUserId());
        } else {
            violationReportDTO.setAuthorId(userService.findByUsername(authorName).getId());
            violationReportRepository.save(violationReportMapper.toEntity(violationReportDTO));

            return ApplicationResponse
                    .builder()
                    .messages(List.of("User with id " + violationReportDTO.getReportedUserId() + " reported"))
                    .timeStamp(formatter.format(Instant.now()))
                    .build();
        }
    }

    @Override
    public Collection<ViolationReportDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<ViolationReport> violationReports = violationReportRepository.findAll(pageable);
        List<ViolationReport> violationReportList = violationReports.getContent();
        return violationReportList
                .stream()
                .map(violationReportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
