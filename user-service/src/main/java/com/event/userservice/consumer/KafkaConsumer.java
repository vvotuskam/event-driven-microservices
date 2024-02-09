package com.event.userservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(
            topics = "${spring.kafka.demo-topic}",
            groupId = "kafka-listener",
            containerFactory = "containerFactory"
    )
    public void itemListener(@Payload String payload) {
        log.info("Received {} from Kafka", payload);
    }
}
