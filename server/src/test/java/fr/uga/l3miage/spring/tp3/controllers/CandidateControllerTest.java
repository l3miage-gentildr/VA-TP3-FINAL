package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")public class CandidateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @SpyBean
    private CandidateComponent candidateComponent;

    @BeforeEach
    public void clear(){
        candidateRepository.deleteAll();
    }

    @Test
    public void getCandidateAverageFound(){

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParam = new HashMap<>();

        final CandidateEntity candidateEntity1 = CandidateEntity.builder()
                .id(1L)
                .email("mvl@jesuisunmail.com")
                .build();

        final ExamEntity examEntity1 = ExamEntity.builder()
                .id(1L)
                .weight(1)
                .build();

        final CandidateEvaluationGridEntity candidateEvaluationGridEntity1 = CandidateEvaluationGridEntity.builder()
                .candidateEntity(candidateEntity1)
                .grade(12)
                .build();

        urlParam.put("idCandidate", 1L);

        candidateEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1));
        examEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity1));
        candidateRepository.save(candidateEntity1);

        ResponseEntity<Double> response = testRestTemplate.exchange("/api/candidates/{idCandidate}/average", HttpMethod.GET, new HttpEntity<>(null, headers), Double.class,urlParam);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getCandidateAverageNotFound(){
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParms = new HashMap<>();

        // If we put a String here instead of a long, we will get a BAD_REQUEST error instead of a NOT_FOUND
        urlParms.put("idCandidate", 99999L);

        ResponseEntity<NotFoundException> response = testRestTemplate.exchange("/api/candidates/{idCandidate}/average", HttpMethod.GET, new HttpEntity<>(headers), NotFoundException.class, urlParms);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
