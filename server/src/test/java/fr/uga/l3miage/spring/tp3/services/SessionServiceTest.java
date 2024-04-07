package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @MockBean
    private SessionComponent sessionComponent;

    @MockBean
    private ExamComponent examComponent;

    @SpyBean
    private SessionMapper sessionMapper;

    @Test
    public void testCreateSession() {
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

        when(sessionMapper.toEntity(sessionCreationRequest)).thenReturn(EcosSessionEntity.builder().build());
        when(sessionComponent.createSession(any(EcosSessionEntity.class))).thenReturn(EcosSessionEntity.builder().build());

        SessionResponse sessionResponse = sessionService.createSession(sessionCreationRequest);

        assertNotNull(sessionResponse);

    }
}
