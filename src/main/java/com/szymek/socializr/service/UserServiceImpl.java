package com.szymek.socializr.service;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.mapper.UserMapper;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SocialGroupRepository socialGroupRepository;

    @Override
    public Collection<UserDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate");
        Page<User> users = userRepository.findAll(pageable);
        List<User> usersList = users.getContent();
        return usersList
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO update(UserDTO userToUpdate, Long userId) {
        return userRepository
                .findById(userId)
                .map(user -> {
                            if (user.getFirstName() != null) {
                                user.setFirstName(userToUpdate.getFirstName());
                            }
                            if (user.getLastName() != null) {
                                user.setLastName(userToUpdate.getLastName());
                            }
                            return userMapper.toUserDTO(userRepository.save(user));
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
    }

    @Override
    public void joinGroup(Long userId, Long socialGroupId) {
        userRepository
                .findById(userId)
                .map(user -> {
                            SocialGroup socialGroup = socialGroupRepository.findById(socialGroupId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
                            user.getSocialGroups().add(socialGroup);
                            socialGroup.getMembers().add(user);
                            socialGroupRepository.save(socialGroup);
                            return userRepository.save(user);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
    }

    @Override
    public void leaveGroup(Long userId, Long socialGroupId) {
        userRepository
                .findById(userId)
                .map(user -> {
                            SocialGroup socialGroup = socialGroupRepository.findById(socialGroupId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
                            user.getSocialGroups().remove(socialGroup);
                            socialGroup.getMembers().remove(user);
                            socialGroupRepository.save(socialGroup);
                            return userRepository.save(user);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
    }
}
