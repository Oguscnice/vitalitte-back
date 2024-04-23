package fr.vitalitte.vitalittebackend.giftCardUsed.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import fr.vitalitte.vitalittebackend.giftCardUsed.usecase.GiftCardUsedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/giftCardsUsed")
public class GiftCardUsedController {
    GiftCardUsedService giftCardUsedService;

    public GiftCardUsedController(GiftCardUsedService giftCardUsedService) {this.giftCardUsedService = giftCardUsedService;}

    @PostMapping("")
    public ResponseEntity<MessageResponse> createGiftCardUsed(@RequestBody CreateGiftCardUsedBody createGiftCardUsedBody) {
        this.giftCardUsedService.createGifCardUsed(createGiftCardUsedBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Carte Cadeau utilisée avec succès."));
    }

    @GetMapping("/giftCard/{giftCardCode}")
    public List<GiftCardUsedDto> findAllGiftCardsUsedByGiftCardCode(@PathVariable String giftCardCode) {
        return this.giftCardUsedService.findAllGiftCardsUsedByGiftCardCode(giftCardCode);
    }
    @GetMapping("")
    public List<GiftCardUsedDto> findAllGiftCardsUsed() {return this.giftCardUsedService.findAllGiftCardsUsed();}
}
