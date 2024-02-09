package user.management.properties;

import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Getter
public class DefaultKafkaProperties {
    private final Properties properties;
    private final String demoTopic;

    public DefaultKafkaProperties() {
        this.properties = new Properties();

        this.properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        this.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.demoTopic = "demo-topic";
    }
}
