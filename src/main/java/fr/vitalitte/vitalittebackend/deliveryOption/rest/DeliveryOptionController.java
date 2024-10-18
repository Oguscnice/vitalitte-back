package fr.vitalitte.vitalittebackend.deliveryOption.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.deliveryOption.usecase.DeliveryOptionService;
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
@RequestMapping("/api/delivery-option")
public class DeliveryOptionController {

    DeliveryOptionService deliveryOptionService;

    public DeliveryOptionController(DeliveryOptionService deliveryOptionService) {
        this.deliveryOptionService = deliveryOptionService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createDeliveryOption(@RequestBody CreateDeliveryOptionBody createDeliveryOptionBody) {
        this.deliveryOptionService.createDeliveryOption(createDeliveryOptionBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Option de Livraison créée avec succès."));
    }

    @GetMapping("/is-available")
    public List<DeliveryOptionDto> getAllDeliveryOptionsAvailable() {
        return this.deliveryOptionService.getDeliveryOptionsIsAvailable(true);
    }

    @GetMapping("")
    public List<DeliveryOptionDto> getAllDeliveryOptions() {
        return this.deliveryOptionService.getAllDeliveryOptions();
    }

    @PutMapping("/change-availability")
    public ResponseEntity<MessageResponse> changeDeliveryOptionIsAvailability(@RequestBody DeliveryOptionDto deliveryOptionDto) {
        this.deliveryOptionService.changeDeliveryOptionAvailability(deliveryOptionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Option de Livraison mise à jour avec succès."));
    }

    @PutMapping("/change-express")
    public ResponseEntity<MessageResponse> changeDeliveryOptionIsExpress(@RequestBody DeliveryOptionDto deliveryOptionDto) {
        this.deliveryOptionService.changeDeliveryOptionExpress(deliveryOptionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Option de Livraison mise à jour avec succès."));
    }

    @PutMapping("")
    public ResponseEntity<MessageResponse> updateDeliveryOption(@RequestBody DeliveryOptionDto deliveryOptionDto) {
        this.deliveryOptionService.updateDeliveryOption(deliveryOptionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Option de Livraison mise à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteDeliveryOption(@PathVariable("slug") String slug) {
        this.deliveryOptionService.deleteDeliveryOption(slug);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Option de Livraison supprimée avec succès."));
    }
}
