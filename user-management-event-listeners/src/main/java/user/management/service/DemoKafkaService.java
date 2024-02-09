package user.management.service;

import lombok.extern.slf4j.Slf4j;
import user.management.dto.KafkaPayload;
import user.management.dto.UserPayload;

@Slf4j
public class DemoKafkaService implements KafkaService {

    @Override
    public void publish(KafkaPayload payload) {
        log.info("DEMO KAFKA IMPL!!!");
        log.info("Payload: {}", payload);
    }
}
