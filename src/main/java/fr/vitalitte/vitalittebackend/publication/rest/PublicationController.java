package fr.vitalitte.vitalittebackend.publication.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.publication.usecase.PublicationService;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/publications")
public class PublicationController {

    PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createPublication(@RequestBody CreatePublicationBody createPublicationBody) {
        this.publicationService.createPublication(createPublicationBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Publication créé avec succès."));
    }

    @GetMapping("/isSpotlighted/{boolbool}")
    public List<PublicationDto> getPublicationsSpotlighted(@PathVariable boolean boolbool) {
        return this.publicationService.getPublicationsSpotlighted(boolbool);
    }

    @PostMapping("/paginated")
    public Page<PublicationDto> getPublicationsPaginatedBySearchValue(@RequestBody PaginationItemBySearchValue paginationItemBySearchValue) {
        return this.publicationService.getPublicationsPaginatedBySearchValue(paginationItemBySearchValue);
    }

    @GetMapping("/{slug}")
    public PublicationDto getPublicationBySlug(@PathVariable String slug) {
        return this.publicationService.getPublicationBySlug(slug);
    }

    @PutMapping("/spotlighted")
    public ResponseEntity<MessageResponse> updateSpotlightedPublication(@RequestBody PublicationDto publicationDto) {
        PublicationDto publicationDtoToChangeSpotlight =  this.publicationService.changePublicationSpotlight(publicationDto);
        String message = String.format("Publication %s est %s.", publicationDtoToChangeSpotlight.getTitle(), publicationDtoToChangeSpotlight.isSpotlighted() ? "mise en avant" : "n'est plus en avant");
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updatePublicationBySlug(@PathVariable String slug, @RequestBody PublicationDto publicationDto) {
        this.publicationService.updatePublicationBySlug(publicationDto);
        return ResponseEntity.ok(new MessageResponse("Publication mis à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deletePublication(@PathVariable String slug) {
        this.publicationService.deletePublicationBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Publication supprimée avec succès."));
    }
}
