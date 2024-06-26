package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import fr.uga.l3miage.spring.tp3.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private SessionService sessionService;

    @SpyBean
    private SessionComponent sessionComponent;

    @SpyBean
    private SessionMapper sessionMapper;

    @Test
    public void testCreateSession() {
    //public EcosSessionEntity createSession(EcosSessionEntity entity)
        final HttpHeaders headers = new HttpHeaders();

        SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest1 = SessionProgrammationStepCreationRequest.builder()
                .code("1")
                .description("step1")
                .build();

        SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest.builder()
                .steps(Set.of(sessionProgrammationStepCreationRequest1))
                .build();

        SessionCreationRequest sessionCreationRequest = SessionCreationRequest.builder()
                .name("session1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .examsId(Set.of(1L, 2L))
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();

        ResponseEntity<String> response = testRestTemplate.exchange("/api/sessions/create", HttpMethod.POST, new HttpEntity<>(sessionCreationRequest, headers), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }
}
