package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.UnauthorizedException;
import com.szymek.socializr.exception.UserAlreadyInGroupException;
import com.szymek.socializr.exception.UsernameTakenException;
import com.szymek.socializr.mapper.UserMapper;
import com.szymek.socializr.model.Role;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.UserRepository;
import com.szymek.socializr.security.SignUpRequest;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SocialGroupService socialGroupService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @Override
    public Collection<UserDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<User> users = userRepository.findAll(pageable);
        List<User> usersList = users.getContent();
        return usersList
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = findUserByUsername(username);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO create(SignUpRequest signUpRequest) {
        boolean isNonUnique = userRepository.existsByUsername(signUpRequest.getUsername());
        if (isNonUnique) throw new UsernameTakenException(signUpRequest.getUsername());
        User user = userMapper.toEntity(signUpRequest);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public ApplicationResponse deleteById(Long userId) {
        String message;
        if (userRepository.findById(userId).isPresent()) {
            message = String.format("User with ID: %s has been deleted", userId);
            userRepository.deleteById(userId);
        } else {
            message = String.format("User with ID: %s doesn't exist", userId);
        }
        return ApplicationResponse
                .builder()
                .messages(List.of(message))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public UserDTO update(UserDTO userToUpdate, String loggedUserName) {
        checkPermission(userToUpdate.getId(), loggedUserName, "edit", "user");
        Long userId = userToUpdate.getId();
        return userRepository
                .findById(userId)
                .map(user -> {
                            if (user.getFirstName() != null) {
                                user.setFirstName(userToUpdate.getFirstName());
                            }
                            if (user.getLastName() != null) {
                                user.setLastName(userToUpdate.getLastName());
                            }
                            return userMapper.toDTO(userRepository.save(user));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
    }

    @Override
    public ApplicationResponse joinGroup(String loggedUserName, Long socialGroupId) {
        User user = findUserByUsername(loggedUserName);
        SocialGroup socialGroup = socialGroupService.findSocialGroupById(socialGroupId);
        if (socialGroup.getMembers().contains(user)) {
            throw new UserAlreadyInGroupException(user.getId(), socialGroupId);
        }
        user.getSocialGroups().add(socialGroup);
        userRepository.save(user);
        return ApplicationResponse
                .builder()
                .messages(List.of(
                        String.format("User with ID: %s has joined the group with ID: %s", user.getId(), socialGroupId)
                ))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public ApplicationResponse leaveGroup(String loggedUserName, Long socialGroupId) {
        User user = findUserByUsername(loggedUserName);
        SocialGroup socialGroup = socialGroupService.findSocialGroupById(socialGroupId);
        user.getSocialGroups().remove(socialGroup);
        userRepository.save(user);
        return ApplicationResponse
                .builder()
                .messages(List.of(
                        String.format("User with ID: %s has left the group with ID: %s", user.getId(), socialGroupId)
                ))
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }

    @Override
    public void checkPermission(Long objectId, String loggedUserName, String operation, String entityName) {
        User user = userRepository.findByUsername(loggedUserName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", loggedUserName));
        if (!user.getId().equals(objectId) && !user.getRole().equals(Role.ADMIN))
            throw new UnauthorizedException(operation, entityName);
    }

    public User findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    public Collection<UserDTO> findAllBySocialGroupId(Long socialGroupId, String loggedUserName, Integer page, Integer size) {
        socialGroupService.checkSocialGroupPermission(socialGroupId, loggedUserName);
        SocialGroup socialGroup = socialGroupService.findSocialGroupById(socialGroupId);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<User> socialGroupMembers = userRepository.findUsersBySocialGroups(socialGroup, pageable);
        List<User> socialGroupMembersList = socialGroupMembers.getContent();
        return socialGroupMembersList
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
