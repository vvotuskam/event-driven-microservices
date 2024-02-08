package user.management.kafka.properties;

import lombok.Getter;

import java.util.Properties;

@Getter
public class DefaultKafkaProperties {
    private final Properties properties;
    private final String demoTopic;

    public DefaultKafkaProperties() {
        this.properties = new Properties();

        this.properties.put("bootstrap.servers", "localhost:9092");
        this.properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.demoTopic = "demo-topic";
    }
}
