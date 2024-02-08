package com.event.userservice.keycloak.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserCreationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Boolean emailVerified;
    private Boolean enabled;
    private String[] requiredActions;
    private KeycloakCredentials[] credentials;

    public KeycloakUserCreationRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = email;
        this.emailVerified = true;
        this.enabled = true;
        this.requiredActions = new String[0];
        this.credentials = new KeycloakCredentials[]{new KeycloakCredentials(password)};
    }
}
