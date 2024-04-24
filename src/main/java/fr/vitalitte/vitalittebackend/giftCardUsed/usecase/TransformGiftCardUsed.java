package fr.vitalitte.vitalittebackend.giftCardUsed.usecase;

import fr.vitalitte.vitalittebackend.gifCard.exception.GifCardNotFoundException;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import fr.vitalitte.vitalittebackend.gifCard.persistence.GiftCardRepository;
import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;
import fr.vitalitte.vitalittebackend.gifCard.usecase.TransformGiftCard;
import fr.vitalitte.vitalittebackend.giftCardUsed.models.GiftCardUsed;
import fr.vitalitte.vitalittebackend.giftCardUsed.persistence.GiftCardUsedRepository;
import fr.vitalitte.vitalittebackend.giftCardUsed.rest.GiftCardUsedDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransformGiftCardUsed {
    GiftCardUsedRepository giftCardUsedRepository;
    GiftCardRepository giftCardRepository;
    TransformGiftCard transformGiftCard;

    public TransformGiftCardUsed(GiftCardUsedRepository giftCardUsedRepository, GiftCardRepository giftCardRepository, TransformGiftCard transformGiftCard) {
        this.giftCardUsedRepository = giftCardUsedRepository;
        this.giftCardRepository = giftCardRepository;
        this.transformGiftCard = transformGiftCard;
    }

    public GiftCardUsedDto giftCardUsedToDto(GiftCardUsed giftCardUsed){
        GiftCardDto giftCardDtoFound = this.transformGiftCard.giftCardToDto(this.giftCardRepository.findByCode(giftCardUsed.getGiftCard().getCode())
                                                                                                        .orElseThrow(GifCardNotFoundException::new));
        return GiftCardUsedDto.builder()
                .firstname(giftCardUsed.getFirstname())
                .lastname(giftCardUsed.getLastname())
                .email(giftCardUsed.getEmail())
                .phone(giftCardUsed.getPhone())
                .createdAt(giftCardUsed.getCreatedAt())
                .giftCardDto(giftCardDtoFound)
                .build();
    }

    public List<GiftCardUsedDto> giftCardsUsedtoDtos(List<GiftCardUsed> giftCards){
        return mapList(this::giftCardUsedToDto, giftCards);
    }

    public GiftCardUsed dtoToGiftCardUsed(GiftCardUsedDto giftCardUsedDto){
        GiftCard giftCardFound = this.giftCardRepository.findByCode(giftCardUsedDto.getGiftCardDto().getCode())
                                                            .orElseThrow(GifCardNotFoundException::new);
        return this.giftCardUsedRepository.findByEmailAndGiftCard(giftCardUsedDto.getEmail(), giftCardFound);
    }

    public  List<GiftCardUsed> dtosToGiftCardsUsed(List<GiftCardUsedDto> giftCardsUsedDto){
        return mapList(this::dtoToGiftCardUsed, giftCardsUsedDto);
    }
}
