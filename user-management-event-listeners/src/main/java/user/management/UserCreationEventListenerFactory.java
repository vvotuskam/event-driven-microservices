package user.management;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import user.management.kafka.DemoKafkaService;

public class UserCreationEventListenerFactory implements EventListenerProviderFactory {

    private static final String ID = "custom-user-creation-even-listener";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new UserCreationEventListener(session, new DemoKafkaService());
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
