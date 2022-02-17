package com.szymek.socializr.repository;

import com.szymek.socializr.model.ContactInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
}
