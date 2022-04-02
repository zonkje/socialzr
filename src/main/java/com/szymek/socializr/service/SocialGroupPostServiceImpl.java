package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.dto.SocialGroupPostDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.ThumbUpException;
import com.szymek.socializr.mapper.PostLabelMapper;
import com.szymek.socializr.mapper.PostThumbUpMapper;
import com.szymek.socializr.mapper.SocialGroupPostMapper;
import com.szymek.socializr.model.*;
import com.szymek.socializr.repository.PostThumbUpRepository;
import com.szymek.socializr.repository.SocialGroupPostRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SocialGroupPostServiceImpl implements SocialGroupPostService {

    private final SocialGroupPostRepository socialGroupPostRepository;
    private final SocialGroupPostMapper socialGroupPostMapper;
    private final PostLabelMapper postLabelMapper;
    private final PostThumbUpRepository postThumbUpRepository;
    private final PostThumbUpMapper postThumbUpMapper;
    private final UserService userService;
    private final SocialGroupService socialGroupService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    //TODO: -a lot of redundant code, consider optimization
    @Override
    public Collection<SocialGroupPostDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<SocialGroupPost> socialGroupPosts = socialGroupPostRepository.findAll(pageable);
        List<SocialGroupPost> socialGroupPostList = socialGroupPosts.getContent();
        return socialGroupPostList
                .stream()
                .map(socialGroupPostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SocialGroupPostDTO findById(Long socialGroupPostId) {
        SocialGroupPost socialGroupPost = findSocialGroupPostById(socialGroupPostId);
        return socialGroupPostMapper.toDTO(socialGroupPost);
    }

    @Override
    public SocialGroupPostDTO create(SocialGroupPostDTO socialGroupPostDTO, String authorName) {
        socialGroupPostDTO.setAuthorId(userService.findByUsername(authorName).getId());
        SocialGroupPost socialGroupPost = socialGroupPostRepository.save(socialGroupPostMapper.toEntity(socialGroupPostDTO));
        socialGroupPost.setPostLabels(postLabelMapper.map(socialGroupPostDTO.getPostLabels(), socialGroupPost));
        return socialGroupPostMapper.toDTO(socialGroupPostRepository.save(socialGroupPost));
    }

    @Override
    public SocialGroupPostDTO update(SocialGroupPostDTO socialGroupPostToUpdate, String loggedUserName) {
        userService.checkPermission(socialGroupPostToUpdate.getAuthorId(), loggedUserName, "edit",
                "social group post");
        Long socialGroupPostId = socialGroupPostToUpdate.getId();
        return socialGroupPostRepository
                .findById(socialGroupPostId)
                .map(socialGroupPost -> {
                            if (socialGroupPost.getText() != null) {
                                socialGroupPost.setText(socialGroupPostToUpdate.getText());
                            }
                            return socialGroupPostMapper.toDTO(socialGroupPostRepository.save(socialGroupPost));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", socialGroupPostId));
    }

    @Override
    public ApplicationResponse deleteById(Long socialGroupPostId, String loggedUserName) {
        SocialGroupPost socialGroupPost = findSocialGroupPostById(socialGroupPostId);
        userService.checkPermission(socialGroupPost.getAuthor().getId(), loggedUserName, "delete",
                "social group post");
        String message = String.format("Post with ID: %s has been deleted", socialGroupPostId);
        socialGroupPostRepository.deleteById(socialGroupPostId);
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public Collection<SocialGroupPostDTO> findAllByLabelId(Long labelId, String loggedUserName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<SocialGroupPost> socialGroupPosts = socialGroupPostRepository.findSocialGroupPostsByPostLabelsId(labelId, pageable);
        List<SocialGroupPost> socialGroupPostList = socialGroupPosts.getContent();
        User user = userService.findUserByUsername(loggedUserName);

        return socialGroupPostList
                .stream()
                .filter(socialGroupPost -> {
                    SocialGroup socialGroup = socialGroupPost.getSocialGroup();
                    return (user.getRole().equals(Role.ADMIN) || socialGroup.getAccessLevel().equals(AccessLevel.PUBLIC)
                            || user.getSocialGroups().contains(socialGroup));
                })
                .map(socialGroupPostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SocialGroupPostDTO> findAllByLabelName(String labelName, String loggedUserName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<SocialGroupPost> socialGroupPosts = socialGroupPostRepository.findSocialGroupPostsByPostLabelsName(labelName, pageable);
        List<SocialGroupPost> socialGroupPostList = socialGroupPosts.getContent();
        User user = userService.findUserByUsername(loggedUserName);

        return socialGroupPostList
                .stream()
                .filter(socialGroupPost -> {
                    SocialGroup socialGroup = socialGroupPost.getSocialGroup();
                    return (user.getRole().equals(Role.ADMIN) || socialGroup.getAccessLevel().equals(AccessLevel.PUBLIC)
                            || user.getSocialGroups().contains(socialGroup));
                })
                .map(socialGroupPostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SocialGroupPostDTO> findAllBySocialGroupId(Long socialGroupId, String loggedUserName, Integer page, Integer size) {
        socialGroupService.checkSocialGroupPermission(socialGroupId, loggedUserName);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<SocialGroupPost> socialGroupPosts = socialGroupPostRepository.findSocialGroupPostsBySocialGroupId(socialGroupId, pageable);
        List<SocialGroupPost> socialGroupPostList = socialGroupPosts.getContent();

        return socialGroupPostList
                .stream()
                .map(socialGroupPostMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostThumbUpDTO addThumbUpToPost(PostThumbUpDTO postThumbUpDTO, String authorName) {
        SocialGroupPost socialGroupPost = findSocialGroupPostById(postThumbUpDTO.getPostId());
        socialGroupService.checkSocialGroupPermission(socialGroupPost.getSocialGroup().getId(), authorName);

        boolean isAlreadyThumbUpByUser = socialGroupPost.getPostThumbUps().stream()
                .anyMatch(
                        postThumbUp -> postThumbUp.getAuthor().getId().equals(postThumbUpDTO.getAuthorId())
                );

        if (!isAlreadyThumbUpByUser) {
            postThumbUpDTO.setAuthorId(userService.findByUsername(authorName).getId());
            PostThumbUp postThumbUp = postThumbUpMapper.toEntity(postThumbUpDTO);
            return postThumbUpMapper.toDTO(postThumbUpRepository.save(postThumbUp));
        } else {
            throw new ThumbUpException(postThumbUpDTO.getPostId(), postThumbUpDTO.getAuthorId());
        }

    }

    @Override
    public ApplicationResponse deletePostThumbUpById(Long thumbUpId, String loggedUserName) {
        PostThumbUp postThumbUpToDelete = postThumbUpRepository.findById(thumbUpId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Thumb Up", "ID", thumbUpId));
        userService.checkPermission(postThumbUpToDelete.getAuthor().getId(), loggedUserName, "delete",
                "post thumb up");
        String message = String.format("Post Thumb Up with ID: %s has been deleted", thumbUpId);
        postThumbUpRepository.deleteById(thumbUpId);
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    private SocialGroupPost findSocialGroupPostById(Long socialGroupPostId) {
        return socialGroupPostRepository
                .findById(socialGroupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", socialGroupPostId));
    }

}
