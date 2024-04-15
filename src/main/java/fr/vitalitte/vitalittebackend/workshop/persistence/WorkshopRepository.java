package fr.vitalitte.vitalittebackend.workshop.persistence;

import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkshopRepository extends JpaRepository<Workshop, String> {
    boolean existsBySlug(String slug);
    Optional<Workshop> findBySlug(String slug);
}
