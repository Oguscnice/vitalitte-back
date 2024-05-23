package fr.vitalitte.vitalittebackend.workshop.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.common.models.Pagination;
import fr.vitalitte.vitalittebackend.workshop.usecase.WorkshopService;
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
@RequestMapping("/api/workshops")
public class WorkshopController {
    WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createWorkshop(@RequestBody CreateWorkshopBody createWorkshopBody) {
        this.workshopService.createWorkshop(createWorkshopBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Atelier créé avec succès."));
    }

    @GetMapping("/date-to-come")
    public List<WorkshopDto> getWorkshopsByDateToCome() {
        return this.workshopService.findWorkshopsByDateToCome();
    }

    @PostMapping("/past-date/paginated")
    public List<WorkshopDto> getWorkshopsPaginatedByPastDate(@RequestBody Pagination pagination) {
        System.out.println(pagination.getPage());
        return this.workshopService.findWorkshopsPaginatedByPastDate(pagination);
    }

    @GetMapping("/past-date/counter")
    public Long getCounterWorkshopsByPastDate() {
        return this.workshopService.getCounterWorkshopsByPastDate();
    }
    @GetMapping("/isAvailable/{value}")
    public List<WorkshopDto> getWorkshopByisAvailable(@PathVariable boolean value) {
        return this.workshopService.findWorkshopsIsAvailable(value);
    }

    @GetMapping("/{slug}")
    public WorkshopDto getWorkshopBySlug(@PathVariable String slug) {
        return this.workshopService.getWorkbookBySlug(slug);
    }

    @GetMapping("")
    public List<WorkshopDto> getAllWorkshops() {
        return this.workshopService.findAllWorkshops();
    }

    @PutMapping("/availability")
    public ResponseEntity<MessageResponse> updateAvailabilityWorkshop(@RequestBody WorkshopDto workshopDto) {
        WorkshopDto workshopDtoUpdated =  this.workshopService.changeWorkshopAvailability(workshopDto);
        String message = String.format("Atelier %s est %s.", workshopDtoUpdated.getTitle(), workshopDtoUpdated.isAvailable() ? "Disponible" : "Indisponible");
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updateWorkshopBySlug(@PathVariable String slug, @RequestBody WorkshopDto workshopDto) {
        this.workshopService.updateWorkshopBySlug(workshopDto);
        return ResponseEntity.ok(new MessageResponse("Atelier mis à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteWorkshop(@PathVariable String slug) {
        this.workshopService.deleteWorkshopBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Atelier supprimé avec succès."));
    }
}
