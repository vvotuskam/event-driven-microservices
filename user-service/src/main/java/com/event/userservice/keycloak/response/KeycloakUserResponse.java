package com.event.userservice.keycloak.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakUserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
