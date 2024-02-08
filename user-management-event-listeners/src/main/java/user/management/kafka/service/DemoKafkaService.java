package user.management.kafka.service;

import lombok.extern.slf4j.Slf4j;
import user.management.kafka.dto.Actions;
import user.management.kafka.dto.UserPayload;

@Slf4j
public class DemoKafkaService implements KafkaService {

    @Override
    public void publish(Actions action, UserPayload payload) {
        log.info("DEMO KAFKA IMPL!!!");
        log.info("Action: {}, payload: {}", action, payload);
    }
}
