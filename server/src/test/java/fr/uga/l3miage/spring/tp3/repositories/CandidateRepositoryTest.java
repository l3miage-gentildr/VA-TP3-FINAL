package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

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
    void findAllByTestCenterEntityCode() {
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
}
