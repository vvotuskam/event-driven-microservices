package com.event.userservice.keycloak;

import com.event.userservice.keycloak.request.KeycloakTokenRequest;
import com.event.userservice.keycloak.response.KeycloakTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "token-client",
        url = "${keycloak.url}"
)
public interface TokenClient {
    @PostMapping(
            path = "/realms/master/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    ResponseEntity<KeycloakTokenResponse> getAdminToken(
            @RequestBody KeycloakTokenRequest request
    );
}
