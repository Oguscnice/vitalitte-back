package fr.vitalitte.vitalittebackend.gifCard.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.gifCard.usecase.GiftCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/giftCards")
public class GiftCardController {

    GiftCardService giftCardService;

    public GiftCardController(GiftCardService giftCardService) {this.giftCardService = giftCardService;}

    @PostMapping("")
    public ResponseEntity<MessageResponse> createGiftCard(@RequestBody CreateGiftCardBody createGiftCardBody) {
        this.giftCardService.createGiftCard(createGiftCardBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Carte Cadeau créée avec succès."));
    }

    @GetMapping("/is-already-used/{code}/{email}")
    public boolean isGiftCardAlreadyUsedByEmail(@PathVariable String code, @PathVariable String email) {
        return this.giftCardService.isGiftCardAlreadyUsedByEmail(code, email);
    }

    @GetMapping("/user/{code}/{email}")
    public GiftCardDto findGiftCardByCodeForUser(@PathVariable String code, @PathVariable String email) {
        return this.giftCardService.findGiftCardByCodeForUser(code, email);
    }

    @GetMapping("/{code}")
    public GiftCardDto findGiftCardByCode(@PathVariable String code) {
        return this.giftCardService.findGiftCardByCode(code);
    }

    @GetMapping("")
    public List<GiftCardDto> findAllGiftCards() {
        return this.giftCardService.findAllGiftCards();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<MessageResponse> deleteGiftCard(@PathVariable String code) {
        this.giftCardService.deleteGiftCardByCode(code);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Carte Cadeau supprimée avec succès."));
    }
}
