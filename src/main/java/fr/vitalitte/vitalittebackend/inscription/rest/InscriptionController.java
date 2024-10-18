package fr.vitalitte.vitalittebackend.inscription.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.inscription.usecase.InscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {
    InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @PostMapping("")
    public ResponseEntity<InscriptionDto> createInscription(@RequestBody CreateInscriptionBody createInscriptionBody) {
        InscriptionDto inscriptionDto = this.inscriptionService.createInscription(createInscriptionBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscriptionDto);
    }

    @GetMapping("/count-by-workshop/{workshopSlug}")
    public Long getInscriptionsCounterByWorkshop(@PathVariable String workshopSlug) {
        return this.inscriptionService.countInscriptionsByWorkshopSlug(workshopSlug);
    }

    @GetMapping("/workshop/{workshopSlug}")
    public List<InscriptionDto> getInscriptionsByWorkshop(@PathVariable String workshopSlug) {
        return this.inscriptionService.findAllInscriptionsByWorkshop(workshopSlug);
    }

    @GetMapping("/{slug}")
    public InscriptionDto getInscriptionBySlug(@PathVariable String slug) {
        return this.inscriptionService.findInscription(slug);
    }

    @GetMapping("")
    public List<InscriptionDto> getAllInscriptions() {
        return this.inscriptionService.findAllInscriptions();
    }

    @PutMapping("/{addOrRemoveParticipant}")
    public ResponseEntity<MessageResponse> changeQuantityInscriptionBySlug(@PathVariable String addOrRemoveParticipant, @RequestBody InscriptionDto inscriptionDto) {
        this.inscriptionService.changeQuantityInscription(addOrRemoveParticipant, inscriptionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Quantité d'inscription(s) modifiée(s) avec succés."));
    }

    @PutMapping("/confirm")
    public ResponseEntity<MessageResponse> confirmInscriptionBySlug(@RequestBody String inscriptionSlug) {
        String message = this.inscriptionService.confirmInscriptionBySlug(inscriptionSlug);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(message));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteInscriptionBySlug(@PathVariable String slug) {
        this.inscriptionService.deleteInscriptionBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Inscription(s) supprimé(s) avec succès."));
    }
}
