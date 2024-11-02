package fr.vitalitte.vitalittebackend.gifCard.usecase;

import fr.vitalitte.vitalittebackend.gifCard.exception.GifCardNotFoundException;
import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import fr.vitalitte.vitalittebackend.gifCard.persistence.GiftCardRepository;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import org.springframework.stereotype.Service;

import java.util.List;
import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

@Service
public class TransformGiftCard {

    GiftCardRepository giftCardRepository;

    public TransformGiftCard(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public GiftCardDto giftCardToDto(GiftCard giftCard) {
        return GiftCardDto.builder()
                .code(giftCard.getCode())
                .rising(giftCard.getRising())
                .expiryDate(giftCard.getExpiryDate())
                .isPercentage(giftCard.isPercentage())
                .isSingleUse(giftCard.isSingleUse())
                .build();
    }

    public List<GiftCardDto> giftCardsToDtos(List<GiftCard> giftCards) {
        return mapList(this::giftCardToDto, giftCards);
    }

    public GiftCard dtoToGiftCard(GiftCardDto giftCardDto) {
        return this.giftCardRepository.findByCode(giftCardDto.getCode())
                .orElseThrow(GifCardNotFoundException::new);
    }

    public List<GiftCard> dtosToGiftCards(List<GiftCardDto> giftCardDtos) {
        return mapList(this::dtoToGiftCard, giftCardDtos);
    }
}
