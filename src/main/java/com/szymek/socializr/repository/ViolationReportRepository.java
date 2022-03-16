package com.szymek.socializr.repository;

import com.szymek.socializr.model.ViolationReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolationReportRepository extends CrudRepository<ViolationReport, Long> {
}
