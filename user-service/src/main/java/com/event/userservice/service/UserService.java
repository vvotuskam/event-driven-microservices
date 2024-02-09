package com.event.userservice.service;

import com.event.userservice.keycloak.request.KeycloakUserCreationRequest;
import com.event.userservice.keycloak.response.KeycloakUserResponse;

import java.util.List;

public interface UserService {

    KeycloakUserResponse getById(String id);

    List<KeycloakUserResponse> getUsers();

    void createUser(KeycloakUserCreationRequest request);

    void deleteUser(String id);

}
