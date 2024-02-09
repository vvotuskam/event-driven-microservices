package com.event.userservice.config;

import com.event.userservice.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public NewTopic demoTopic() {
        return TopicBuilder.name(kafkaProperties.getDemoTopic()).build();
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<?> containerFactory(
            ConsumerFactory<String, Object> consumerFactory
    ) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }

    private Map<String, Object> consumerConfig() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, "kafka-listener",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*"
        );
    }
}
