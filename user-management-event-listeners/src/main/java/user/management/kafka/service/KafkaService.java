package user.management.kafka.service;

import user.management.kafka.dto.Actions;
import user.management.kafka.dto.UserPayload;

public interface KafkaService {
    void publish(Actions action, UserPayload payload);
}
