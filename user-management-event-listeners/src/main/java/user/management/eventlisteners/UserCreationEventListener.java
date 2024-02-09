package user.management.eventlisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import user.management.dto.Actions;
import user.management.dto.UserPayload;
import user.management.dto.UserRepresentation;
import user.management.properties.RealmProperties;
import user.management.service.KafkaService;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class UserCreationEventListener implements EventListenerProvider {

    private final KeycloakSession keycloakSession;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    public UserCreationEventListener(KeycloakSession keycloakSession, KafkaService kafkaService, ObjectMapper objectMapper) {
        this.keycloakSession = keycloakSession;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onEvent(Event event) {
        if (!event.getType().equals(EventType.REGISTER)) return;

        RealmModel realm = keycloakSession.realms().getRealm(event.getRealmId());
        if (!realm.getName().equals(RealmProperties.DEMO)) return;

        UserModel registeredUser = keycloakSession.users().getUserById(realm, event.getUserId());

        log.info("User registration event for user {}", registeredUser.getEmail());

        UserPayload payload = UserPayload.builder()
                .id(registeredUser.getId())
                .email(registeredUser.getEmail())
                .firstname(registeredUser.getFirstName())
                .lastname(registeredUser.getLastName())
                .action(Actions.CREATED)
                .build();

        kafkaService.publish(payload);
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

        try {
            UserRepresentation userRepresentation = objectMapper.readValue(event.getRepresentation(), UserRepresentation.class);

            UserModel user = findUserByEmail(userRepresentation.getEmail(), realm)
                    .orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

            UserPayload payload = UserPayload.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstname(user.getFirstName())
                    .lastname(user.getLastName())
                    .action(Actions.CREATED)
                    .build();

            kafkaService.publish(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }

    private Optional<UserModel> findUserByEmail(String email, RealmModel realm) {
        Map<String, String> params = Map.of(UserModel.EMAIL, email);
        return keycloakSession.users().searchForUserStream(realm, params).findFirst();
    }
}
