package user.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import user.management.dto.UserPayload;
import user.management.properties.DefaultKafkaProperties;

@Slf4j
@RequiredArgsConstructor
public class DefaultKafkaService implements KafkaService {

    private final DefaultKafkaProperties kafkaProperties;

    @Override
    @SneakyThrows
    public void publish(UserPayload payload) {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Sending message to kafka with payload{{}}", payload);

        Producer<String, String> producer = new KafkaProducer<>(kafkaProperties.getProperties());

        ProducerRecord<String, String> record = new ProducerRecord<>(
                kafkaProperties.getDemoTopic(), mapper.writeValueAsString(payload)
        );

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Exception during sending message to kafka", exception);
            } else {
                log.info("Message sent successfully to topic {}, partition {}, offset {}",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });

        producer.close();
    }
}
