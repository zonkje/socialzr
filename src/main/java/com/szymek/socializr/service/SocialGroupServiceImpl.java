package com.szymek.socializr.service;

import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SocialGroupServiceImpl implements SocialGroupService{

    private final SocialGroupRepository socialGroupRepository;

    public SocialGroupServiceImpl(SocialGroupRepository socialGroupRepository) {
        this.socialGroupRepository = socialGroupRepository;
    }

    @Override
    public Collection<SocialGroup> findAll() {
        return (Collection<SocialGroup>) socialGroupRepository.findAll();
    }

    @Override
    public SocialGroup findById(Long socialGroupId) {
        return socialGroupRepository.findById(socialGroupId).get();
    }

    @Override
    public SocialGroup create(SocialGroup socialGroup) {
        return socialGroupRepository.save(socialGroup);
    }

    @Override
    public void delete(SocialGroup object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
