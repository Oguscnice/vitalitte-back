package fr.vitalitte.vitalittebackend.notebook.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.notebook.usecase.NotebookService;
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
    @GetMapping("/{slug}")
    public NotebookDto getNotebookBySlug(@PathVariable String slug) {
        return this.notebookService.getNotebookBySlug(slug);
    }
    @GetMapping("")
    public List<NotebookDto> getAllNotebooks() {
        return this.notebookService.findAllNotebooks();
    }

    @PutMapping("/availability/{slug}")
    public ResponseEntity<MessageResponse> updateAvailabilityNotebookBySlug(@PathVariable String slug, @RequestBody NotebookDto notebookDto) {
        this.notebookService.changeNotebookAvailabilityBySlug(slug, notebookDto);
        return ResponseEntity.ok(new MessageResponse("Carnet mis à jour avec succès."));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updateCategoryBySlug(@PathVariable String slug, @RequestBody NotebookDto notebookDto) {
        this.notebookService.updateNotebookBySlug(slug, notebookDto);
        return ResponseEntity.ok(new MessageResponse("Carnet mis à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteNotebook(@PathVariable String slug) {
        this.notebookService.deleteNotebookBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Carnet supprimé avec succès."));
    }
}
