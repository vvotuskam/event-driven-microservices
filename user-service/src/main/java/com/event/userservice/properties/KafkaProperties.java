package com.event.userservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("spring.kafka")
public class KafkaProperties {

    private String demoTopic;
    private String bootstrapServers;
}
