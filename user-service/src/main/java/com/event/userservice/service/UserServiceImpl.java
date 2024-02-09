package com.event.userservice.service;

import com.event.userservice.entity.KeycloakUser;
import com.event.userservice.keycloak.TokenClient;
import com.event.userservice.keycloak.UserClient;
import com.event.userservice.keycloak.request.KeycloakTokenRequest;
import com.event.userservice.keycloak.request.KeycloakUserCreationRequest;
import com.event.userservice.keycloak.response.KeycloakTokenResponse;
import com.event.userservice.keycloak.response.KeycloakUserResponse;
import com.event.userservice.properties.KeycloakAdminCliProperties;
import com.event.userservice.utils.KeycloakUserMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenClient tokenClient;
    private final UserClient userClient;
    private final KeycloakAdminCliProperties adminCliProperties;
    private final KeycloakUserService keycloakUserService;
    private final KeycloakUserMapper mapper;

    private KeycloakTokenResponse getAdminToken() {
        KeycloakTokenRequest request = adminCliProperties.getRequest();
        ResponseEntity<KeycloakTokenResponse> adminToken = tokenClient.getAdminToken(request);
        return adminToken.getBody();
    }

    @Override
    public KeycloakUserResponse getById(@NonNull String id) {
        try {
            Optional<KeycloakUser> optionalUser = keycloakUserService.getById(id);

            if (optionalUser.isPresent())
                return mapper.toResponse(optionalUser.get());

            return getUsers().stream()
                    .filter(user -> id.equals(user.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (FeignException e) {
            log.error("Fetching exception");
            throw new RuntimeException(e);
        }
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
