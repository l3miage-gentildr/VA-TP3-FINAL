package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private TestCenterRepository testCenterRepository ;

    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository ;

    @BeforeEach
    public void clear() {
        candidateRepository.deleteAll();
        testCenterRepository.deleteAll();
        candidateEvaluationGridRepository.deleteAll();
    }

    @Test
    void testFindAllByTestCenterEntityCode() {
    // Set<CandidateEntity> findAllByTestCenterEntityCode(TestCenterCode code);
        TestCenterEntity testCenterEntityGRE = TestCenterEntity.builder()
                .code(TestCenterCode.GRE)
                .build();

        CandidateEntity candidateEntityGRE = CandidateEntity.builder()
                .email("magnuscarlsen@jesuisunmail.com")
                .testCenterEntity(testCenterEntityGRE)
                .build();

        testCenterRepository.save(testCenterEntityGRE);
        candidateRepository.save(candidateEntityGRE);

        TestCenterEntity testCenterEntityDIJ = TestCenterEntity.builder()
                .code(TestCenterCode.DIJ)
                .build();

        CandidateEntity candidateEntityDIJ = CandidateEntity.builder()
                .email("mvl@jesuisunmail.com")
                .testCenterEntity(testCenterEntityDIJ)
                .build();

        testCenterRepository.save(testCenterEntityDIJ);
        candidateRepository.save(candidateEntityDIJ);

        Set<CandidateEntity> candidateEntities = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);

        assertThat(candidateEntities).hasSize(1);
        assertThat(candidateEntities.stream().findFirst().get().getTestCenterEntity().getCode()).isEqualTo(TestCenterCode.GRE);

    }

    @Test
    void testFindAllByCandidateEvaluationGridEntitiesGradeLessThan() {
    // Set<CandidateEntity> findAllByCandidateEvaluationGridEntitiesGradeLessThan(double grade);
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .email("magnuscarlsen@jesuisunmail.com")
                .birthDate(LocalDate.of(1990,11,30))
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .candidateEntity(candidateEntity)
                .build();

        candidateRepository.save(candidateEntity);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity);

        CandidateEntity candidateEntity2 = CandidateEntity.builder()
                .email("mvl@jesuisunmail.com")
                .birthDate(LocalDate.of(1990,10,21))
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity.builder()
                .grade(4)
                .candidateEntity(candidateEntity2)
                .build();

        candidateRepository.save(candidateEntity2);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity2);

        Set<CandidateEntity> candidateEntities = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10);

        assertThat(candidateEntities).hasSize(1);
        assertThat(candidateEntities.stream().findFirst().get().getBirthDate()).isEqualTo(LocalDate.of(1990,10,21));
    }

    @Test
    void testFindAllByHasExtraTimeFalseAndBirthDateBefore() {
    // Set<CandidateEntity> findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate localDate);
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .email("magnuscarlsen@jesuisunmail.com")
                .birthDate(LocalDate.of(1990,11,30))
                .hasExtraTime(false)
                .build();


        CandidateEntity candidateEntity2 = CandidateEntity.builder()
                .email("mvl@jesuisunmail.com")
                .birthDate(LocalDate.of(1990,10,21))
                .hasExtraTime(true)
                .build();

        CandidateEntity candidateEntity3 = CandidateEntity.builder()
                .email("alirezafirouzja@jesuisunmail.com")
                .birthDate(LocalDate.of(2003,06,21))
                .hasExtraTime(false)
                .build();

        CandidateEntity candidateEntity4 = CandidateEntity.builder()
                .email("dingliren@jesuisunmail.com")
                .birthDate(LocalDate.of(1992,10,24))
                .hasExtraTime(false)
                .build();

        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);
        candidateRepository.save(candidateEntity4);

        Set<CandidateEntity> candidateEntities = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2000,01,01));

        assertThat(candidateEntities).hasSize(2);
        assertThat(candidateEntities.stream().findFirst().get().getBirthDate()).isEqualTo(LocalDate.of(1990,11,30));
    }
}
