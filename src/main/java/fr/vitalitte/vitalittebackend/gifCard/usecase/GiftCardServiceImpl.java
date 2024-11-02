package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
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
    SlugifyUtil slugifyUtil;

    public GiftCardServiceImpl(GiftCardRepository giftCardRepository, TransformGiftCard transformGiftCard, SlugifyUtil slugifyUtil) {
        this.giftCardRepository = giftCardRepository;
        this.transformGiftCard = transformGiftCard;
        this.slugifyUtil = slugifyUtil;
    }

    @Override
    public void createGiftCard(CreateGiftCardBody createGiftCardBody){

        String slugifiedCode = slugifyUtil.stringToSlug(createGiftCardBody.getCode());

        if (existsByCode(slugifiedCode)) {
            throw new SlugOrCodeGiftCardAlreadyExistsException();
        }

        final GiftCard newGiftCard = GiftCard.builder()
                .code(slugifiedCode)
                .rising(createGiftCardBody.getRising())
                .expiryDate(createGiftCardBody.getExpiryDate())
                .isPercentage(createGiftCardBody.isPercentage())
                .isSingleUse(createGiftCardBody.isSingleUse())
                .build();

        this.giftCardRepository.save(newGiftCard);
    }

    public List<GiftCardDto> findAllGiftCards() {
        return this.transformGiftCard.giftCardsToDtos(this.giftCardRepository.findAll());
    }

    @Override
    public boolean isGiftCardAlreadyUsedByEmail(String code, String email) {
        //TODO : vérifier que la carte cadeau n'ai pas été utilisé
        // et l'usage unique
//        if (repo de commande.findAllByEmailAndGiftCard) {
//            return true;
//        }
        return false;
    }

    @Override
    public GiftCardDto findGiftCardByCodeForUser(String code, String email) {
        GiftCard giftCard = findOneGiftCardOrThrow(code);

        if (giftCard.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new GiftCardExpiredException();
        }

        if (isGiftCardAlreadyUsedByEmail(code, email)) {
            throw new GiftCardAlreadyUsedException();
        }

        return this.transformGiftCard.giftCardToDto(giftCard);
    }

    @Override
    public GiftCardDto findGiftCardByCode(String code) {
        GiftCard giftCardFound = findOneGiftCardOrThrow(code);
        return this.transformGiftCard.giftCardToDto(giftCardFound);
    }

    @Override
    public void deleteGiftCardByCode(String code) {
        GiftCard giftCardToDelete = findOneGiftCardOrThrow(code);
        this.giftCardRepository.delete(giftCardToDelete);
    }

    private GiftCard findOneGiftCardOrThrow(String code) {
        return this.giftCardRepository.findByCode(code).orElseThrow(GifCardNotFoundException::new);
    }

    private boolean existsByCode(String code) {
        return this.giftCardRepository.existsByCode(code);
    }
}

