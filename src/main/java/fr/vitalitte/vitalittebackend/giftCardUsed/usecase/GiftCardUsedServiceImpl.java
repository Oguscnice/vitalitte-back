package fr.vitalitte.vitalittebackend.giftCardUsed.usecase;

import fr.vitalitte.vitalittebackend.gifCard.exception.GifCardNotFoundException;
import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import fr.vitalitte.vitalittebackend.gifCard.persistence.GiftCardRepository;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import fr.vitalitte.vitalittebackend.gifCard.usecase.TransformGiftCard;
import fr.vitalitte.vitalittebackend.giftCardUsed.exception.GiftCardAlreadyUsedException;
import fr.vitalitte.vitalittebackend.giftCardUsed.models.GiftCardUsed;
import fr.vitalitte.vitalittebackend.giftCardUsed.persistence.GiftCardUsedRepository;
import fr.vitalitte.vitalittebackend.giftCardUsed.rest.CreateGiftCardUsedBody;
import fr.vitalitte.vitalittebackend.giftCardUsed.rest.GiftCardUsedDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCardUsedServiceImpl implements GiftCardUsedService {
    GiftCardUsedRepository giftCardUsedRepository;
    GiftCardRepository giftCardRepository;
    TransformGiftCard transformGiftCard;
    TransformGiftCardUsed transformGiftCardUsed;

    public GiftCardUsedServiceImpl(GiftCardUsedRepository giftCardUsedRepository, GiftCardRepository giftCardRepository, TransformGiftCard transformGiftCard, TransformGiftCardUsed transformGiftCardUsed) {
        this.giftCardUsedRepository = giftCardUsedRepository;
        this.giftCardRepository = giftCardRepository;
        this.transformGiftCard = transformGiftCard;
        this.transformGiftCardUsed = transformGiftCardUsed;
    }

    public void createGifCardUsed(CreateGiftCardUsedBody createGiftCardUsedBody){

        GiftCard giftCardFound = this.giftCardRepository.findByCode(createGiftCardUsedBody.getGiftCardDto().getCode())
                .orElseThrow(GifCardNotFoundException::new);

        if(this.giftCardUsedRepository.existsByEmailAndGiftCard(createGiftCardUsedBody.getEmail(), giftCardFound)){
            throw new GiftCardAlreadyUsedException();
        }

        final GiftCardUsed newGiftCardUsed = GiftCardUsed.builder()
                .firstname(createGiftCardUsedBody.getFirstname())
                .lastname(createGiftCardUsedBody.getLastname())
                .email(createGiftCardUsedBody.getEmail())
                .phone(createGiftCardUsedBody.getPhone())
                .giftCard(giftCardFound)
                .build();

        this.giftCardUsedRepository.save(newGiftCardUsed);
    };

    public List<GiftCardUsedDto> findAllGiftCardsUsed(){
        return this.transformGiftCardUsed.giftCardsUsedtoDtos(this.giftCardUsedRepository.findAll());
    };

    public List<GiftCardUsedDto> findAllGiftCardsUsedByGiftCardCode(String code) {
        GiftCard giftCardFound = this.giftCardRepository.findByCode(code)
                                                            .orElseThrow(GifCardNotFoundException::new);
        return this.transformGiftCardUsed.giftCardsUsedtoDtos(this.giftCardUsedRepository.findAllByGiftCard(giftCardFound));
    };
}
