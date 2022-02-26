package com.szymek.socializr.service;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.SocialGroupMapper;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocialGroupServiceImpl implements SocialGroupService{

    private final SocialGroupRepository socialGroupRepository;
    private final SocialGroupMapper socialGroupMapper;

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
    public void delete(SocialGroupDTO object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
