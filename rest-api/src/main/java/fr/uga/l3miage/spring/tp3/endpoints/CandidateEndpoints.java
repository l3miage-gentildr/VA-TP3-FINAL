package fr.uga.l3miage.spring.tp3.endpoints;

import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Gestion des candidat")
@RestController
@RequestMapping("/api/candidates")
public interface CandidateEndpoints {


    @Operation(description = "Calculer la moyenne d'un étudiant en fonction de sont ...")
    @ApiResponse(responseCode = "200", description = "La note à pu être calculer")
    @ApiResponse(responseCode = "404", description = "Le candidat n'a pas été trouvé" , content = @Content(schema = @Schema(implementation = CandidatNotFoundResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{candidateId}/average")
    Double getCandidateAverage(@PathVariable Long candidateId);

    // Bonjour et bienvenue au centre d'enrichissement assisté par ordinateur, d'Aperture Science.
    @Operation(description = "Ajoute une collection d'étudiants à un centre de test")
    @ApiResponse(responseCode = "202", description = "Réussite de l'ajout des étudiants au centre de test")
    @ApiResponse(responseCode = "404", description = "Le centre de test ou les étudiants n'existent pas", content = @Content)
    @ApiResponse(responseCode = "400", description = "La requête est incorrecte", content = @Content)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{apertureScienceId}/addTestSubjects")
    Boolean addCandidatesToTestCenter(@PathVariable Long apertureScienceId, @RequestBody Set<Long> testSubjectsIds);
}
