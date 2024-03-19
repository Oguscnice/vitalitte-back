package fr.vitalitte.vitalittebackend.notebook.persistence;

import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, String> {
    boolean existsBySlug(String slug);
    Optional<Notebook> findBySlug(String slug);
    List<Notebook> findAllByCategorySlug(String categorySlug);
}
