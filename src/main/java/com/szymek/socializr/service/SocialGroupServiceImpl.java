package com.szymek.socializr.service;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.SocialGroupMapper;
import com.szymek.socializr.mapper.UserMapper;
import com.szymek.socializr.model.AccessLevel;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.SocialGroupRepository;
import com.szymek.socializr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocialGroupServiceImpl implements SocialGroupService {

    private final SocialGroupRepository socialGroupRepository;
    private final SocialGroupMapper socialGroupMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        SocialGroup socialGroup = socialGroupRepository
                .findById(socialGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
        return socialGroupMapper.toDTO(socialGroup);
    }

    @Override
    public SocialGroupDTO create(SocialGroupDTO socialGroupDTO) {
        SocialGroup socialGroup = socialGroupMapper.toEntity(socialGroupDTO);
        return socialGroupMapper.toDTO(socialGroupRepository.save(socialGroup));
    }

    @Override
    public void deleteById(Long socialGroupId) {
        socialGroupRepository.deleteById(socialGroupId);
    }

    @Override
    public SocialGroupDTO update(SocialGroupDTO socialGroupToUpdate, Long socialGroupId) {
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
                            return socialGroupMapper.toDTO(socialGroupRepository.save(socialGroup));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
    }

    @Override
    public Collection<UserDTO> findAllMembers(Long socialGroupId, Integer page, Integer size) {
        SocialGroup socialGroup = socialGroupRepository
                .findById(socialGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<User> socialGroupMembers = userRepository.findUsersBySocialGroups(socialGroup, pageable);
        List<User> socialGroupMembersList = socialGroupMembers.getContent();
        return socialGroupMembersList
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
