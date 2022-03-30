package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.ContactInformationMapper;
import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.ContactInformationRepository;
import com.szymek.socializr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInformationServiceImpl implements ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;
    private final ContactInformationMapper contactInformationMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    //TODO -delete this
    @Override
    public Collection<ContactInformationDTO> findAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public ContactInformationDTO findById(Long contactInformationId) {
        ContactInformation contactInformation = contactInformationRepository
                .findById(contactInformationId).orElseThrow(() -> new ResourceNotFoundException("Contact Information",
                        "ID", contactInformationId));
        return contactInformationMapper.toDTO(contactInformation);
    }

    @Override
    public ContactInformationDTO create(ContactInformationDTO contactInformationDTO, String authorName) {
        User user = userRepository.findByUsername(authorName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", authorName));
        ContactInformation contactInformation = contactInformationMapper.toEntity(contactInformationDTO);
        contactInformationRepository.save(contactInformation);
        user.setContactInformation(contactInformation);
        userRepository.save(user);
        return contactInformationMapper.toDTO(contactInformation);
    }

    @Override
    public ApplicationResponse deleteById(Long contactInformationId, String loggedUserName) {
        String message;
        if (contactInformationRepository.findById(contactInformationId).isPresent()) {
            message = String.format("Contact information with ID: %s has been deleted", contactInformationId);
            contactInformationRepository.deleteById(contactInformationId);
        } else {
            message = String.format("Contact information with ID: %s doesn't exist", contactInformationId);
        }
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    //bidirectional binding will make it much simpler
    @Override
    public ContactInformationDTO update(ContactInformationDTO contactInformationToUpdate, String loggedUserName) {
        userService.checkPermission(contactInformationToUpdate.getId(), loggedUserName, "edit",
                "contact information");
        Long contactInformationId = contactInformationToUpdate.getId();
        return contactInformationRepository
                .findById(contactInformationId)
                .map(contactInformation -> {
                            if (contactInformation.getEmail() != null) {
                                contactInformation.setEmail(contactInformationToUpdate.getEmail());
                            }
                            if (contactInformation.getPhoneNumber() != null) {
                                contactInformation.setPhoneNumber(contactInformationToUpdate.getPhoneNumber());
                            }
                            if (contactInformation.getWebsitesURLs() != null) {
                                contactInformation.setWebsitesURLs(contactInformationToUpdate.getWebsitesURLs());
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
                            return contactInformationMapper.toDTO(contactInformationRepository.save(contactInformation));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Contact Information",
                        "ID", contactInformationId));
    }
}
