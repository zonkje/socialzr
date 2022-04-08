package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.UnauthorizedException;
import com.szymek.socializr.mapper.SocialGroupMapper;
import com.szymek.socializr.model.AccessLevel;
import com.szymek.socializr.model.Role;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.SocialGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class SocialGroupServiceImpl implements SocialGroupService {

    private final SocialGroupRepository socialGroupRepository;
    private final SocialGroupMapper socialGroupMapper;
    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @Autowired
    public SocialGroupServiceImpl(SocialGroupRepository socialGroupRepository, SocialGroupMapper socialGroupMapper, @Lazy UserService userService) {
        this.socialGroupRepository = socialGroupRepository;
        this.socialGroupMapper = socialGroupMapper;
        this.userService = userService;
    }

    @Override
    public Collection<SocialGroupDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<SocialGroup> socialGroups = socialGroupRepository.findAll(pageable);
        List<SocialGroup> socialGroupsList = socialGroups.getContent();
        return socialGroupsList
                .stream()
                .map(socialGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SocialGroupDTO findById(Long socialGroupId) {
        SocialGroup socialGroup = findSocialGroupById(socialGroupId);
        return socialGroupMapper.toDTO(socialGroup);
    }

    @Override
    public SocialGroupDTO create(SocialGroupDTO socialGroupDTO, String authorName) {
        socialGroupDTO.setCreatorId(userService.findByUsername(authorName).getId());
        SocialGroup socialGroup = socialGroupMapper.toEntity(socialGroupDTO);
        return socialGroupMapper.toDTO(socialGroupRepository.save(socialGroup));
    }

    @Override
    public ApplicationResponse deleteById(Long socialGroupId, String loggedUserName) {
        SocialGroup socialGroup = findSocialGroupById(socialGroupId);
        userService.checkPermission(socialGroup.getCreator().getId(), loggedUserName, "delete", "social group");
        String message = String.format("Social group with ID: %s has been deleted", socialGroupId);
        socialGroupRepository.deleteById(socialGroupId);

        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public SocialGroupDTO update(SocialGroupDTO socialGroupToUpdate, String loggedUserName) {
        userService.checkPermission(socialGroupToUpdate.getCreatorId(), loggedUserName, "edit", "social group");
        Long socialGroupId = socialGroupToUpdate.getId();
        return socialGroupRepository
                .findById(socialGroupId)
                .map(socialGroup -> {
                            if (socialGroup.getName() != null) {
                                socialGroup.setName(socialGroupToUpdate.getName());
                            }
                            if (socialGroup.getDescription() != null) {
                                socialGroup.setDescription(socialGroupToUpdate.getDescription());
                            }
                            if (socialGroup.getAccessLevel() != null) {
                                String accessLevelString = socialGroupToUpdate.getAccessLevel();
                                if (accessLevelString.equals("PRIVATE")) {
                                    socialGroup.setAccessLevel(AccessLevel.PRIVATE);
                                } else if (accessLevelString.equals("PUBLIC")) {
                                    socialGroup.setAccessLevel(AccessLevel.PUBLIC);
                                }
                            }
                            if (socialGroup.getAvatarUrl() != null) {
                                socialGroup.setAvatarUrl(socialGroupToUpdate.getAvatarUrl());
                            }
                            return socialGroupMapper.toDTO(socialGroupRepository.save(socialGroup));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
    }

    public SocialGroup findSocialGroupById(Long socialGroupId) {
        return socialGroupRepository
                .findById(socialGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
    }

    public void checkSocialGroupPermission(Long socialGroupId, String username) {
        SocialGroup socialGroup = findSocialGroupById(socialGroupId);
        User user = userService.findUserByUsername(username);
        if (socialGroup.getAccessLevel().equals(AccessLevel.PRIVATE) && !user.getRole().equals(Role.ADMIN) &&
                !socialGroup.getMembers().contains(user)) {
            throw new UnauthorizedException("view members of", "social group");
        }
    }
}