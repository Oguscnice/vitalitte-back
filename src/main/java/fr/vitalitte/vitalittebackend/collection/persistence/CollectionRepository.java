package fr.vitalitte.vitalittebackend.collection.persistence;

import fr.vitalitte.vitalittebackend.collection.models.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {
    Optional<Collection> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}
