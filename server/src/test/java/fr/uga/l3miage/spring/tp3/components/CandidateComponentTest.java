package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateComponentTest {

    @Autowired
    private CandidateComponent candidateComponent;

    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    public void testGetAllEliminatedCandidateEmpty() {
    //Set<CandidateEntity> getAllEliminatedCandidate();

        when(candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(anyDouble())).thenReturn(new HashSet<>());

        Set<CandidateEntity> candidateEntities = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10);

        assertThat(candidateEntities).isEmpty();
    }

    @Test
    public void testGetAllEliminatedCandidateNotEmpty() {
    //Set<CandidateEntity> getAllEliminatedCandidate();

            CandidateEntity candidateEntity1 = CandidateEntity.builder()
                    .email("mvl@jesuisunmail.com")
                    .build();

        CandidateEntity candidateEntity2 = CandidateEntity.builder()
                .email("anatolikarpov@jesuisunmail.com")
                .build();

            Set<CandidateEntity> candidateEntities = new HashSet<>();
            candidateEntities.add(candidateEntity1);
            candidateEntities.add(candidateEntity2);

            when(candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(anyDouble())).thenReturn(candidateEntities);

            Set<CandidateEntity> candidateEntitiesResponse = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10);

            assertThat(candidateEntitiesResponse).isNotEmpty();
            assertThat(candidateEntitiesResponse).hasSize(2);

    }

    @Test
    public void testGetCandidatByIdFound() {
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(132212L)
                .email("mvl@jesuisunmail.com")
                .build();

        when(candidateRepository.findById(132212L)).thenReturn(Optional.of(candidateEntity));

        assertDoesNotThrow(()->candidateComponent.getCandidatById(132212L));
    }

    @Test
    public void testGetCandidatByIdNotFound() {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then - when
        assertThrows(CandidateNotFoundException.class,()->candidateComponent.getCandidatById(anyLong()));
    }


}
