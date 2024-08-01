package fr.vitalitte.vitalittebackend.stationery.materials.persistence;

import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    boolean existsBySlug(String slug);
    Optional<Material> findBySlug(String slug);
    List<Material> findAllMaterialsByIsAvailableForCustomizationOrderByName(boolean value);
    Page<Material> findAllByNameContainsIgnoreCaseOrderByName(String name, Pageable pageable);
}
