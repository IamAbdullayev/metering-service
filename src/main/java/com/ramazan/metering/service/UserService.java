package com.ramazan.metering.service;

import com.ramazan.metering.controller.dto.UserResponseDto;
import com.ramazan.metering.exception.NotFoundException;
import com.ramazan.metering.mapper.UserMapper;
import com.ramazan.metering.model.entity.User;
import com.ramazan.metering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public User getOrCreateUser(String sub) {
        log.info("Looking up user with subjectId={}", sub);
        return userRepository.findBySub(sub)
                .orElseGet(() -> {
                    log.info("User with subjectId={} not found, creating new one", sub);
                    User newUser = User.builder()
                            .sub(sub)
                            .build();
                    User saved = userRepository.save(newUser);
                    log.info("Created new user with id={} and subjectId={}", saved.getId(), saved.getSub());
                    return saved;
                });
    }

    public User getBySub(String sub) {
        log.info("Fetching user with subjectId={}", sub);

        return userRepository.findBySub(sub)
                .orElseThrow(() -> new NotFoundException("User not found: " + sub));
    }

    public UserResponseDto createUser(String sub) {
        log.info("Creating new user with sub={}", sub);

        if (userRepository.findBySub(sub).isPresent()) {
            throw new IllegalArgumentException("User with sub=" + sub + " already exists");
        }

        User user = User.builder()
                .sub(sub)
                .build();

        User saved = userRepository.save(user);
        log.info("User created id={} sub={}", saved.getId(), sub);
        return mapper.toDto(saved);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Fetched {} users", users.size());

        return users.stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserResponseDto findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
        log.info("Fetched {} user", user.getId());

        return mapper.toDto(user);
    }
}
