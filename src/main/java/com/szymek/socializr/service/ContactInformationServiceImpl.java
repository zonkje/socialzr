package com.szymek.socializr.service;

import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.repository.ContactInformationRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ContactInformationServiceImpl implements ContactInformationService{

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformationServiceImpl(ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    @Override
    public Collection<ContactInformation> findAll() {
        return null;
    }

    @Override
    public Optional<ContactInformation> findById(Long contactInformationId) {
        return contactInformationRepository.findById(contactInformationId);
    }

    @Override
    public ContactInformation create(ContactInformation contactInformation) {
        return contactInformationRepository.save(contactInformation);
    }

    @Override
    public void delete(ContactInformation object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
