package com.event.userservice.service;

import com.event.userservice.keycloak.TokenClient;
import com.event.userservice.keycloak.UserClient;
import com.event.userservice.keycloak.request.KeycloakTokenRequest;
import com.event.userservice.keycloak.request.KeycloakUserCreationRequest;
import com.event.userservice.keycloak.response.KeycloakTokenResponse;
import com.event.userservice.keycloak.response.KeycloakUserResponse;
import com.event.userservice.properties.KeycloakAdminCliProperties;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenClient tokenClient;
    private final UserClient userClient;
    private final KeycloakAdminCliProperties adminCliProperties;

    private KeycloakTokenResponse getAdminToken() {
        KeycloakTokenRequest request = adminCliProperties.getRequest();
        ResponseEntity<KeycloakTokenResponse> adminToken = tokenClient.getAdminToken(request);
        return adminToken.getBody();
    }

    @Override
    public List<KeycloakUserResponse> getUsers() {
        try {
            KeycloakTokenResponse token = getAdminToken();
            ResponseEntity<List<KeycloakUserResponse>> users = userClient.getAllUsers(token.getBearerToken(), null);
            return users.getBody();
        } catch (FeignException e) {
            log.error("Fetching exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(KeycloakUserCreationRequest request) {
        try {
            String bearerToken = getAdminToken().getBearerToken();
            userClient.createUser(bearerToken, request);
        } catch (FeignException e) {
            log.error("Fetching exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(String id) {
        try {
            String bearerToken = getAdminToken().getBearerToken();
            userClient.deleteUserById(bearerToken, id);
        } catch (FeignException e) {
            log.error("Fetching exception");
            throw new RuntimeException(e);
        }
    }
}
