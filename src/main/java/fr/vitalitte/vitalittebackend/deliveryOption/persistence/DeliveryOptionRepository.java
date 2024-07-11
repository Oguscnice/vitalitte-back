package fr.vitalitte.vitalittebackend.deliveryOption.persistence;

import fr.vitalitte.vitalittebackend.deliveryOption.models.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, String> {
    Optional<DeliveryOption> findBySlug(String slug);
    Boolean existsBySlug(String slug);
    List<DeliveryOption> findAllDeliveryOptionByIsAvailable(boolean value);
}
