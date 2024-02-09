package com.event.userservice.service;

import com.event.userservice.consumer.model.UserRepresentation;
import com.event.userservice.entity.KeycloakUser;
import com.event.userservice.keycloak.response.KeycloakUserResponse;
import com.event.userservice.repository.KeycloakUserRedisRepository;
import com.event.userservice.utils.KeycloakUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final KeycloakUserRedisRepository userRedisRepository;
    private final KeycloakUserMapper mapper;

    @Override
    public List<KeycloakUser> getAll() {
        return (List<KeycloakUser>) userRedisRepository.findAll();
    }

    @Override
    public Optional<KeycloakUser> getById(String id) {
        return userRedisRepository.findById(id);
    }

    @Override
    public void save(KeycloakUserResponse response) {
        userRedisRepository.save(mapper.toInstance(response));
    }

    @Override
    public void save(UserRepresentation representation) {
        userRedisRepository.save(mapper.toInstance(representation));
    }
}
