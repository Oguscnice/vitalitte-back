package fr.vitalitte.vitalittebackend.materials.persistence;

import fr.vitalitte.vitalittebackend.materials.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    boolean existsBySlug(String slug);
    Optional<Material> findBySlug(String slug);
}
