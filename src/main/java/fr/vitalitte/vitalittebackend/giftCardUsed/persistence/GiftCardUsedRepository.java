package fr.vitalitte.vitalittebackend.giftCardUsed.persistence;

import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import fr.vitalitte.vitalittebackend.giftCardUsed.models.GiftCardUsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCardUsedRepository extends JpaRepository<GiftCardUsed, String> {
    List<GiftCardUsed> findAllByGiftCard(GiftCard giftCard);
    GiftCardUsed findByEmailAndGiftCard(String email, GiftCard giftCard);
    boolean existsByEmailAndGiftCard(String email, GiftCard giftCard);
}
