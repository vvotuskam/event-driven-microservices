package com.event.userservice.security;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        String email = extractEmail(jwt);
        List<SimpleGrantedAuthority> roles = extractRoles(jwt);

        User user = new User(email, "", roles);

        return new UsernamePasswordAuthenticationToken(user, "", roles);
    }

    private String extractEmail(Jwt jwt) {
        return jwt.getClaim(JwtClaims.EMAIL_CLAIM);
    }

    @SuppressWarnings("unchecked")
    private List<SimpleGrantedAuthority> extractRoles(Jwt jwt) {
        Map<String, Object> realmClaim = (Map<String, Object>) jwt.getClaims().get(JwtClaims.REALM_CLAIM);
        List<String> roles = (List<String>) realmClaim.get(JwtClaims.ROLES_CLAIM);
        return roles.stream()
                .map(role -> String.format("%s%s", JwtClaims.ROLE_PREFIX, role))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
