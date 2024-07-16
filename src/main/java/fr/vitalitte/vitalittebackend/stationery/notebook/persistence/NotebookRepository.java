package fr.vitalitte.vitalittebackend.stationery.notebook.persistence;

import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, String> {
    boolean existsBySlug(String slug);
    Optional<Notebook> findBySlug(String slug);
    List<Notebook> findAllByCategory(Category category);
    List<Notebook> findAllByCollection(Collection collection);
    List<Notebook> findAllByMaterialsContaining(Material material);

    @Query("SELECT n FROM Notebook n WHERE (:category IS NULL OR n.category = :category) AND (:collection IS NULL OR n.collection = :collection)")
    List<Notebook> findAllByCategoryAndCollection(Category category, Collection collection);
}
