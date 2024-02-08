package user.management;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import user.management.kafka.Actions;
import user.management.kafka.KafkaService;

@Slf4j
public class UserCreationEventListener implements EventListenerProvider {

    private final KeycloakSession keycloakSession;
    private final KafkaService kafkaService;

    public UserCreationEventListener(KeycloakSession keycloakSession, KafkaService kafkaService) {
        this.keycloakSession = keycloakSession;
        this.kafkaService = kafkaService;
    }

    @Override
    public void onEvent(Event event) {
        if (!event.getType().equals(EventType.REGISTER)) return;

        RealmModel realm = keycloakSession.realms().getRealm(event.getRealmId());
        if (!realm.getName().equals(RealmProperties.DEMO)) return;

        UserModel registeredUser = keycloakSession.users().getUserById(realm, event.getUserId());

        kafkaService.publish(Actions.CREATED, registeredUser);
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        if (!event.getOperationType().equals(OperationType.CREATE)) return;

        RealmModel realm = keycloakSession.realms().getRealm(event.getRealmId());
        if (!realm.getName().equals(RealmProperties.DEMO)) return;

        log.info("ADMIN EVENT!!!");
        log.info("Resource as string type: {}", event.getResourceTypeAsString());
        log.info("Resource type: {}", event.getResourceType());
        log.info("Representation: {}", event.getRepresentation());
    }

    @Override
    public void close() {

    }
}
