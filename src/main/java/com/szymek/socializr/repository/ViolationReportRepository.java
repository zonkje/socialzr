package com.szymek.socializr.repository;

import com.szymek.socializr.model.ViolationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolationReportRepository extends JpaRepository<ViolationReport, Long> {
}
