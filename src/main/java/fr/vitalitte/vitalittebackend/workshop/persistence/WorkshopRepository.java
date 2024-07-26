package fr.vitalitte.vitalittebackend.workshop.persistence;

import fr.vitalitte.vitalittebackend.publication.models.Publication;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, String> {
    boolean existsBySlug(String slug);
    Optional<Workshop> findBySlug(String slug);
    List<Workshop> findAllWorkshopByIsAvailable(boolean value);
    List<Workshop> findAllWorkshopByDateAfterOrderByDateDesc(LocalDateTime date);
    Page<Workshop> findWorkshopsByTitleContainsIgnoreCaseAndDateBeforeOrderByDateDesc(String value, LocalDateTime date, Pageable pageable);
}
