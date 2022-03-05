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
public class ContactInformationServiceImpl implements ContactInformationService {

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
    public void deleteById(Long contactInformationId) {
        contactInformationRepository.deleteById(contactInformationId);
    }

    @Override
    public ContactInformationDTO update(ContactInformationDTO contactInformationToUpdate, Long contactInformationId) {
        return contactInformationRepository
                .findById(contactInformationId)
                .map(contactInformation -> {
                            if (contactInformation.getEmail() != null) {
                                contactInformation.setEmail(contactInformationToUpdate.getEmail());
                            }
                            if (contactInformation.getPhoneNumber() != null) {
                                contactInformation.setPhoneNumber(contactInformationToUpdate.getPhoneNumber());
                            }
                            if (contactInformation.getAddress().getAddress() != null) {
                                contactInformation.getAddress().setAddress(contactInformationToUpdate.getAddress().getAddress());
                            }
                            if (contactInformation.getAddress().getCity() != null) {
                                contactInformation.getAddress().setCity(contactInformationToUpdate.getAddress().getCity());
                            }
                            if (contactInformation.getAddress().getState() != null) {
                                contactInformation.getAddress().setState(contactInformationToUpdate.getAddress().getState());
                            }
                            if (contactInformation.getAddress().getCity() != null) {
                                contactInformation.getAddress().setCity(contactInformationToUpdate.getAddress().getCity());
                            }
                            return contactInformationMapper.toContactInformationDTO(contactInformationRepository.save(contactInformation));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Contact Information",
                        "ID", contactInformationId));
    }
}
