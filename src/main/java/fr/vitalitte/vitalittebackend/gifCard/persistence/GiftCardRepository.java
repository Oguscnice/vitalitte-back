package fr.vitalitte.vitalittebackend.gifCard.persistence;

import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, String> {
    Optional<GiftCard> findByCode(String code);
    Boolean existsByCode(String code);
}
