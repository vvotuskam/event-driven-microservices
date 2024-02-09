package com.event.userservice.repository;

import com.event.userservice.entity.KeycloakUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeycloakUserRedisRepository extends CrudRepository<KeycloakUser, String> {
}
