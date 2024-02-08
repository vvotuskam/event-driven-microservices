package com.event.userservice.keycloak.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakCredentials {
    private String type;
    private Boolean temporary;
    private String value;

    public KeycloakCredentials(String password) {
        this.type = "password";
        this.temporary = false;
        this.value = password;
    }
}
