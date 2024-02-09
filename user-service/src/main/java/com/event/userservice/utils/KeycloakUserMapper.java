package com.event.userservice.utils;

import com.event.userservice.consumer.model.UserRepresentation;
import com.event.userservice.entity.KeycloakUser;
import com.event.userservice.keycloak.response.KeycloakUserResponse;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUserMapper {

    public KeycloakUserResponse toResponse(KeycloakUser user) {
        return KeycloakUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .build();
    }

    public KeycloakUser toInstance(KeycloakUserResponse response) {
        return KeycloakUser.builder()
                .id(response.getId())
                .email(response.getEmail())
                .firstname(response.getFirstName())
                .lastname(response.getLastName())
                .build();
    }

    public KeycloakUser toInstance(UserRepresentation representation) {
        return KeycloakUser.builder()
                .id(representation.getId())
                .email(representation.getEmail())
                .firstname(representation.getFirstname())
                .lastname(representation.getLastname())
                .build();
    }
}
