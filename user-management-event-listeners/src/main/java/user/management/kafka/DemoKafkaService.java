package user.management.kafka;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoKafkaService implements KafkaService {

    @Override
    public void publish(Actions action, Object payload) {
        log.info("DEMO KAFKA IMPL!!!");
        log.info("Action: {}, payload: {}", action, payload);
    }
}
