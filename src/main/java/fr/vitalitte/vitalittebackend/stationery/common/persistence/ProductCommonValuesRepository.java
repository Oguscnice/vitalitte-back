package fr.vitalitte.vitalittebackend.stationery.common.persistence;

import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCommonValuesRepository extends JpaRepository<ProductCommonValues, String> {
    Optional<ProductCommonValues> findBySlug(String slug);
}
