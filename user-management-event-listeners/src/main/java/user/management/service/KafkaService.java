package user.management.service;

import user.management.dto.UserPayload;

public interface KafkaService {
    void publish(UserPayload payload);
}
