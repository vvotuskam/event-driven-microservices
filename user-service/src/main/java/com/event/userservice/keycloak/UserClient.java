package com.event.userservice.keycloak;

import com.event.userservice.keycloak.request.KeycloakUserCreationRequest;
import com.event.userservice.keycloak.response.KeycloakUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "user-client",
        url = "${keycloak.url}"
)
public interface UserClient {

    @GetMapping("/admin/realms/demo/users")
    ResponseEntity<List<KeycloakUserResponse>> getAllUsers(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam(name = "email", required = false) String email
    );

    @PostMapping("/admin/realms/demo/users")
    ResponseEntity<Void> createUser(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody KeycloakUserCreationRequest request
    );

    @DeleteMapping("/admin/realms/demo/users/{id}")
    ResponseEntity<Void> deleteUserById(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id
    );
}
