package com.event.userservice.consumer;

import com.event.userservice.consumer.model.UserRepresentation;
import com.event.userservice.service.KeycloakUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KeycloakUserService userService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(
            topics = "${spring.kafka.demo-topic}",
            groupId = "kafka-listener",
            containerFactory = "containerFactory"
    )
    public void itemListener(@Payload String payload) {
        log.info("Received {} from Kafka", payload);

        UserRepresentation representation = objectMapper.readValue(payload, UserRepresentation.class);

        userService.save(representation);

        log.info("User {} cached successfully", payload);
    }
}
