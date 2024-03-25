package fr.vitalitte.vitalittebackend.category.persistence;

import fr.vitalitte.vitalittebackend.category.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, String> {
    Optional<Category> findBySlug(String slug);
    Boolean existsBySlug(String slug);

}
