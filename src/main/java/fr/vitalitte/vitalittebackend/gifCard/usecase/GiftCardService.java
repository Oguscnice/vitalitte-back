package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.gifCard.rest.CreateGiftCardBody;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;

import java.util.List;

public interface GiftCardService {

    void createGiftCard(CreateGiftCardBody createGiftCardBody);
    List<GiftCardDto> findAllGiftCards();
    GiftCardDto findGiftCardByCode(String code);
    boolean isGiftCardAlreadyUsedByEmail(String code, String email);
    GiftCardDto findGiftCardByCodeForUser(String code, String email);
    void deleteGiftCardByCode(String code);

}
