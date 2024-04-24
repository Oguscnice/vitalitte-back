package fr.vitalitte.vitalittebackend.giftCardUsed.usecase;

import fr.vitalitte.vitalittebackend.gifCard.rest.CreateGiftCardBody;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import fr.vitalitte.vitalittebackend.giftCardUsed.models.GiftCardUsed;
import fr.vitalitte.vitalittebackend.giftCardUsed.rest.CreateGiftCardUsedBody;
import fr.vitalitte.vitalittebackend.giftCardUsed.rest.GiftCardUsedDto;

import java.util.List;

public interface GiftCardUsedService {
    void createGifCardUsed(CreateGiftCardUsedBody createGiftCardUsedBody);
    List<GiftCardUsedDto> findAllGiftCardsUsed();
    List<GiftCardUsedDto> findAllGiftCardsUsedByGiftCardCode(String code);

}
