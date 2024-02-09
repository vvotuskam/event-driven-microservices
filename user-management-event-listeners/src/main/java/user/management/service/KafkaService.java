package user.management.service;

import user.management.dto.KafkaPayload;
import user.management.dto.Payload;
import user.management.dto.UserPayload;

public interface KafkaService {
    void publish(KafkaPayload payload);
}
