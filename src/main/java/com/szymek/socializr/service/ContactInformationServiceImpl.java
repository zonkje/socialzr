package com.szymek.socializr.service;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.ContactInformationMapper;
import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.repository.ContactInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ContactInformationServiceImpl implements ContactInformationService{

    private final ContactInformationRepository contactInformationRepository;
    private final ContactInformationMapper contactInformationMapper;

    @Override
    public Collection<ContactInformationDTO> findAll() {
        return null;
    }

    @Override
    public ContactInformationDTO findById(Long contactInformationId) {
        ContactInformation contactInformation = contactInformationRepository
                .findById(contactInformationId).orElseThrow(() -> new ResourceNotFoundException("Contact Information",
                        "ID", contactInformationId));
        return contactInformationMapper.toContactInformationDTO(contactInformation);
    }

    @Override
    public ContactInformationDTO create(ContactInformationDTO contactInformationDTO) {
        ContactInformation contactInformation = contactInformationMapper.toContactInformation(contactInformationDTO);
        return contactInformationMapper.toContactInformationDTO(contactInformationRepository.save(contactInformation));
    }

    @Override
    public void delete(ContactInformationDTO object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
