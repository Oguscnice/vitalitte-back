package fr.vitalitte.vitalittebackend.publication.persistence;

import fr.vitalitte.vitalittebackend.publication.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PublicationRepository extends JpaRepository<Publication, String> {
    boolean existsBySlug(String slug);
    Optional<Publication> findBySlug(String slug);
    List<Publication> findAllPublicationsByIsSpotlighted(boolean value);
    Long countPublicationsByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String title, String description);
    Page<Publication> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Publication> findAllByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCaseOrderByCreatedAtDesc(String title, String description, Pageable pageable);
}
