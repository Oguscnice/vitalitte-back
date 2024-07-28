package fr.vitalitte.vitalittebackend.stationery.notebook.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryAndCollection;
import fr.vitalitte.vitalittebackend.stationery.notebook.usecase.NotebookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notebooks")
public class NotebookController {

    NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createNotebook(@RequestBody CreateNotebookBody createNotebookBody) {
        this.notebookService.createNotebook(createNotebookBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Carnet créé avec succès."));
    }

    @PostMapping("/filtered-by-category-collection")
    public ResponseEntity<List<NotebookDto>> getNotebooksFilteredByCategoryAndCollection(@RequestBody CategoryAndCollection categoryAndCollection) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notebookService.findAllNotebooksFilteredByCategoryAndCollection(categoryAndCollection));
    }

    @GetMapping("/category/{categorySlug}")
    public ResponseEntity<List<NotebookDto>> getNotebookByCategorySlug(@PathVariable String categorySlug) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notebookService.findAllNotebooksByCategorySlug(categorySlug));
    }

    @GetMapping("/collection/{collectionSlug}")
    public ResponseEntity<List<NotebookDto>> getNotebookByCollectionSlug(@PathVariable String collectionSlug) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notebookService.findAllNotebooksByCollectionSlug(collectionSlug));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<NotebookDto> getNotebookBySlug(@PathVariable String slug) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notebookService.getNotebookBySlug(slug));
    }

    @GetMapping("")
    public ResponseEntity<List<NotebookDto>> getAllNotebooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notebookService.findAllNotebooks());
    }

    @PutMapping("/availability")
    public ResponseEntity<MessageResponse> updateAvailabilityNotebook(@RequestBody NotebookDto notebookDto) {
        NotebookDto notebookDtoUpdated =  this.notebookService.changeNotebookAvailability(notebookDto);
        String message = String.format("Carnet %s est %s.", notebookDtoUpdated.getName(), notebookDtoUpdated.isAvailable() ? "Disponible" : "Indisponible");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(message));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updateCategoryBySlug(@PathVariable String slug, @RequestBody NotebookDto notebookDto) {
        this.notebookService.updateNotebookBySlug(slug, notebookDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Carnet mis à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteNotebook(@PathVariable String slug) {
        this.notebookService.deleteNotebookBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Carnet supprimé avec succès."));
    }
}
