package user.management.events.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import user.management.properties.DefaultKafkaProperties;
import user.management.service.DefaultKafkaService;
import user.management.service.KafkaService;

public class UserCreationEventListenerFactory implements EventListenerProviderFactory {

    private static final String ID = "custom-user-creation-even-listener";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        KafkaService kafkaService = new DefaultKafkaService(new DefaultKafkaProperties());
        return new UserCreationEventListener(session, kafkaService, new ObjectMapper());
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
