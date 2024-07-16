package fr.vitalitte.vitalittebackend.stationery.category.persistence;

import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}
