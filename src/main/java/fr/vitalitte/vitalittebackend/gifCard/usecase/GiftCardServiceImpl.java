package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.gifCard.exception.GifCardNotFoundException;
import fr.vitalitte.vitalittebackend.gifCard.exception.SlugOrCodeGiftCardAlreadyExistsException;
import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import fr.vitalitte.vitalittebackend.gifCard.persistence.GiftCardRepository;
import fr.vitalitte.vitalittebackend.gifCard.rest.CreateGiftCardBody;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class GiftCardServiceImpl implements GiftCardService {
    GiftCardRepository giftCardRepository;
    TransformGiftCard transformGiftCard;

    public GiftCardServiceImpl(GiftCardRepository giftCardRepository, TransformGiftCard transformGiftCard) {
        this.giftCardRepository = giftCardRepository;
        this.transformGiftCard = transformGiftCard;
    }

    public void createGiftCard(CreateGiftCardBody createGiftCardBody){

        if (this.giftCardRepository.existsByCode(createGiftCardBody.getCode()) ) {
            throw new SlugOrCodeGiftCardAlreadyExistsException();
        }

        final GiftCard newGiftCard = GiftCard.builder()
                .code(createGiftCardBody.getCode())
                .rising(createGiftCardBody.getRising())
                .expiryDate(createGiftCardBody.getExpiryDate())
                .isPercentage(createGiftCardBody.isPercentage())
                .build();

        this.giftCardRepository.save(newGiftCard);
    };

    public List<GiftCardDto> findAllGiftCards(){
        return this.transformGiftCard.giftCardsToDtos(this.giftCardRepository.findAll());
    };

    public boolean isExpired(String code){
        GiftCard giftCard = this.giftCardRepository.findByCode(code)
                                    .orElseThrow(GifCardNotFoundException::new);
        return giftCard.getExpiryDate().isAfter(LocalDateTime.now());
    };

    public void deleteGiftCardByCode(String code){
        GiftCard giftCardToDelete = this.giftCardRepository.findByCode(code)
                .orElseThrow(GifCardNotFoundException::new);

        this.giftCardRepository.delete(giftCardToDelete);
    };
}

