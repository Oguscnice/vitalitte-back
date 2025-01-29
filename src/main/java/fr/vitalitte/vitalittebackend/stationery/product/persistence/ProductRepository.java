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

    @Query("SELECT COUNT(*) FROM Product p " +
            "WHERE p.eProductType = :eProductType")
    long countByEProductType(EProductType eProductType);

    @Query("SELECT p FROM Product p " +
           "WHERE (:category IS NULL OR p.category = :category) " +
           "AND (:collection IS NULL OR p.collection = :collection) "+
           "AND (:eProductType IS NULL OR p.eProductType = :eProductType)")
    List<Product> findAllByCategoryAndCollectionAndEProductType(Category category, Collection collection, EProductType eProductType);
}
