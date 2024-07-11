package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.gifCard.rest.CreateGiftCardBody;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;

import java.util.List;

public interface GiftCardService {
    void createGiftCard(CreateGiftCardBody createGiftCardBody);
    List<GiftCardDto> findAllGiftCards();
    GiftCardDto findGiftCardByCode(String code);
    GiftCardDto findGiftCardByCodeForUser(String code);
    void deleteGiftCardByCode(String code);
}
