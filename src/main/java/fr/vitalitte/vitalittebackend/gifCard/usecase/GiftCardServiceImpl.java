package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.gifCard.exception.GifCardNotFoundException;
import fr.vitalitte.vitalittebackend.gifCard.exception.GiftCardAlreadyUsedException;
import fr.vitalitte.vitalittebackend.gifCard.exception.GiftCardExpiredException;
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

        if (existsByCode(createGiftCardBody.getCode())) {
            throw new SlugOrCodeGiftCardAlreadyExistsException();
        }

        String slugifiedCode = SlugifyUtil.stringToSlug(createGiftCardBody.getCode());

        final GiftCard newGiftCard = GiftCard.builder()
                .code(slugifiedCode)
                .rising(createGiftCardBody.getRising())
                .expiryDate(createGiftCardBody.getExpiryDate())
                .isPercentage(createGiftCardBody.isPercentage())
                .build();

        this.giftCardRepository.save(newGiftCard);
    };

    public List<GiftCardDto> findAllGiftCards() {
        return this.transformGiftCard.giftCardsToDtos(this.giftCardRepository.findAll());
    };

    public GiftCardDto findGiftCardByCodeForUser(String code) {
        GiftCard giftCard = findOneGiftCardOrThrow(code);

        if (giftCard.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new GiftCardExpiredException();
        }

        //TODO : vérifier que la carte cadeau n'ai pas été utilisé
//        if (repo de commande.findAllByEmailAndGiftCard) {
//            throw new GiftCardAlreadyUsedException();
//        }

        return this.transformGiftCard.giftCardToDto(giftCard);
    };

    public GiftCardDto findGiftCardByCode(String code) {
        GiftCard giftCardFound = findOneGiftCardOrThrow(code);
        return this.transformGiftCard.giftCardToDto(giftCardFound);
    }

    public void deleteGiftCardByCode(String code) {
        GiftCard giftCardToDelete = findOneGiftCardOrThrow(code);
        this.giftCardRepository.delete(giftCardToDelete);
    };

    private GiftCard findOneGiftCardOrThrow(String code) {
        return this.giftCardRepository.findByCode(code).orElseThrow(GifCardNotFoundException::new);
    }

    private boolean existsByCode(String code) {
        return this.giftCardRepository.existsByCode(code);
    }

}

