package com.szymek.socializr.service;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.SocialGroupMapper;
import com.szymek.socializr.mapper.UserMapper;
import com.szymek.socializr.model.AccessLevel;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import com.szymek.socializr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocialGroupServiceImpl implements SocialGroupService {

    private final SocialGroupRepository socialGroupRepository;
    private final SocialGroupMapper socialGroupMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<SocialGroupDTO> findAll() {
        return socialGroupRepository.findAll()
                .stream()
                .map(socialGroupMapper::toSocialGroupDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SocialGroupDTO findById(Long socialGroupId) {
        SocialGroup socialGroup = socialGroupRepository
                .findById(socialGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
        return socialGroupMapper.toSocialGroupDTO(socialGroup);
    }

    @Override
    public SocialGroupDTO create(SocialGroupDTO socialGroupDTO) {
        SocialGroup socialGroup = socialGroupMapper.toSocialGroup(socialGroupDTO);
        return socialGroupMapper.toSocialGroupDTO(socialGroupRepository.save(socialGroup));
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
                            return socialGroupMapper.toSocialGroupDTO(socialGroupRepository.save(socialGroup));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
    }

    @Override
    public Collection<UserDTO> findAllMembers(Long socialGroupId) {
        SocialGroup socialGroup = socialGroupRepository
                .findById(socialGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));

        return userRepository
                .findUsersBySocialGroups(socialGroup)
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
