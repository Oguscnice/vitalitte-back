package fr.vitalitte.vitalittebackend.stationery.collection.rest;

import fr.vitalitte.vitalittebackend.stationery.collection.usecase.CollectionService;
import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
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
@RequestMapping("/api/collections")
public class CollectionController {
    CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {this.collectionService = collectionService;}

    @PostMapping("")
    public ResponseEntity<MessageResponse> createCollection(@RequestBody String collectionName) {
        this.collectionService.createCollection(collectionName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Collection créée avec succès."));
    }

    @GetMapping("")
    public List<CollectionDto> getAllCollections() {
        return this.collectionService.findAllCollections();
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updateCollectionBySlug(@PathVariable String slug, @RequestBody CollectionDto collectionDto) {
        this.collectionService.updateCollection(slug, collectionDto);
        return ResponseEntity.ok(new MessageResponse("Collection mise à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteCollection(@PathVariable String slug) {
        this.collectionService.deleteCollectionBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Collection supprimée avec succès."));
    }
}
