package fr.vitalitte.vitalittebackend.stationery.product.persistence;

import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.productType.models.EProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsBySlug(String slug);
    Optional<Product> findBySlug(String slug);
    List<Product> findAllByCategory(Category category);
    List<Product> findAllByCollection(Collection collection);
    List<Product> findAllByMaterialsContaining(Material material);

    @Query("SELECT n FROM Product n " +
           "WHERE (:category IS NULL OR n.category = :category) " +
           "AND (:collection IS NULL OR n.collection = :collection) "+
           "AND (:eProductType IS NULL OR n.eProductType = :eProductType)")
    List<Product> findAllByCategoryAndCollectionAndEProductType(Category category, Collection collection, EProductType eProductType);
}
