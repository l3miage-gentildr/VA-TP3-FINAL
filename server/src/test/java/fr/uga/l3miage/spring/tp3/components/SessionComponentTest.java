package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.JsonPathAssertions;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionComponentTest {

    @Autowired
    private SessionComponent sessionComponent;

    @MockBean
    private EcosSessionRepository ecosSessionRepository;

    @MockBean
    private EcosSessionProgrammationRepository ecosSessionProgrammationRepository;

    @MockBean
    private EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;

    @Test
    public void testCreateSession() {
    //public EcosSessionEntity createSession(EcosSessionEntity entity)

        EcosSessionProgrammationStepEntity ecosSessionProgrammationStepEntity1 = EcosSessionProgrammationStepEntity.builder()
                .id(1L)
                .code("1")
                .description("step1")
                .build();

        EcosSessionProgrammationStepEntity ecosSessionProgrammationStepEntity2 = EcosSessionProgrammationStepEntity.builder()
                .id(2L)
                .code("2")
                .description("step2")
                .build();

        EcosSessionProgrammationEntity ecosSessionProgrammationEntity = EcosSessionProgrammationEntity.builder()
                .id(1L)
                .ecosSessionProgrammationStepEntities(Set.of(ecosSessionProgrammationStepEntity1, ecosSessionProgrammationStepEntity2))
                .build();

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .id(1L)
                .ecosSessionProgrammationEntity(ecosSessionProgrammationEntity)
                .build();

        when(ecosSessionProgrammationRepository.save(any())).thenReturn(ecosSessionEntity.getEcosSessionProgrammationEntity());
        when(ecosSessionRepository.save(any())).thenReturn(ecosSessionEntity);

        EcosSessionEntity mySession = sessionComponent.createSession(ecosSessionEntity);

        assertThat(mySession).isEqualTo(ecosSessionEntity);
    }

    //Test sessionFailed todo?

}
