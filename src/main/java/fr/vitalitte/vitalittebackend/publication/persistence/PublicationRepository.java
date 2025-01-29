package fr.vitalitte.vitalittebackend.publication.persistence;

import fr.vitalitte.vitalittebackend.publication.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, String> {

    boolean existsBySlug(String slug);
    Optional<Publication> findBySlug(String slug);
    List<Publication> findAllPublicationsByIsSpotlighted(boolean value);

    @Query("SELECT p FROM Publication p " +
            "WHERE (:title IS NULL OR p.title LIKE %:title%) " +
            "AND (:description IS NULL OR p.description LIKE %:description%) " +
            "ORDER BY p.createdAt DESC")
    Page<Publication> findAllByTitleOrDescriptionOrderByCreatedAtDesc(
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable);
}
