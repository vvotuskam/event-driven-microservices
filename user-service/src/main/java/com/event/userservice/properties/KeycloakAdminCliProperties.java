package com.event.userservice.properties;

import com.event.userservice.keycloak.request.KeycloakTokenRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("keycloak.admin")
public class KeycloakAdminCliProperties {
    private String username;
    private String password;
    private String grantType;
    private String clientId;

    public KeycloakTokenRequest getRequest() {
        return new KeycloakTokenRequest(username, password, grantType, clientId);
    }
}
