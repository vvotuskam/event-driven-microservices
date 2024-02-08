package user.management.kafka.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import user.management.kafka.properties.DefaultKafkaProperties;
import user.management.kafka.dto.Actions;
import user.management.kafka.dto.UserPayload;

@RequiredArgsConstructor
public class DefaultKafkaService implements KafkaService {

    private final DefaultKafkaProperties kafkaProperties;

    @Override
    public void publish(Actions action, UserPayload payload) {
        Producer<String, UserPayload> producer = new KafkaProducer<>(kafkaProperties.getProperties());

        ProducerRecord<String, UserPayload> record = new ProducerRecord<>(
                kafkaProperties.getDemoTopic(), payload
        );

        producer.send(record);

        producer.close();
    }
}
