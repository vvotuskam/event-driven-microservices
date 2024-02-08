package com.event.userservice.keycloak.request;

import feign.form.FormProperty;
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
public class KeycloakTokenRequest {

    @FormProperty("username")
    private String username;

    @FormProperty("password")
    private String password;

    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;
}
