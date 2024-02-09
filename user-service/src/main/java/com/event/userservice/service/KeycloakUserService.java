package com.event.userservice.service;

import com.event.userservice.consumer.model.UserRepresentation;
import com.event.userservice.entity.KeycloakUser;
import com.event.userservice.keycloak.response.KeycloakUserResponse;

import java.util.List;
import java.util.Optional;

public interface KeycloakUserService {

    List<KeycloakUser> getAll();

    Optional<KeycloakUser> getById(String id);

    void save(KeycloakUserResponse userResponse);

    void save(UserRepresentation userRepresentation);
}
