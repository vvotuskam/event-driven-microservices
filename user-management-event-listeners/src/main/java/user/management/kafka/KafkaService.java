package user.management.kafka;

public interface KafkaService {
    void publish(Actions action, Object payload);
}
