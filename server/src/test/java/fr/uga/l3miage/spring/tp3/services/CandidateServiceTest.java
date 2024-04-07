package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateComponent candidateComponent;

    @MockBean
    private CandidateEvaluationGridEntity candidateEvaluationGridEntity;

    @Test
    public void getCandidateAverage() throws CandidateNotFoundException {

        CandidateEntity candidateEntity1 = CandidateEntity.builder()
                .id(1L)
                .email("mvl@jesuisunmail.com")
                .build();

        ExamEntity examEntity1 = ExamEntity.builder()
                .id(1L)
                .weight(1)
                .name("exam1")
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity1 = CandidateEvaluationGridEntity.builder()
                .candidateEntity(candidateEntity1)
                .examEntity(examEntity1)
                .grade(12)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity.builder()
                .candidateEntity(candidateEntity1)
                .examEntity(examEntity1)
                .grade(11)
                .build();

        candidateEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1, candidateEvaluationGridEntity2));
        examEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1, candidateEvaluationGridEntity2));

        when(candidateComponent.getCandidatById(anyLong())).thenReturn(candidateEntity1);

        Double expectedAverage = 11.5;
        Double gotAverage = candidateService.getCandidateAverage(1L);

        assertThat(gotAverage).isEqualTo(expectedAverage);
    }

}
