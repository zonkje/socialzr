package com.szymek.socializr.repository;

import com.szymek.socializr.model.SocialGroupPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialGroupPostRepository extends JpaRepository<SocialGroupPost, Long> {

    Page<SocialGroupPost> findSocialGroupPostsByPostLabelsId(Long postLabelId, Pageable pageable);

    Page<SocialGroupPost> findSocialGroupPostsByPostLabelsName(String postLabelName, Pageable pageable);

    Page<SocialGroupPost> findSocialGroupPostsBySocialGroupId(Long socialGroupId, Pageable pageable);

}
