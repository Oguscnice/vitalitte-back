package fr.vitalitte.vitalittebackend.reviews.persistence;

import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.models.Review;
import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, String> {

    boolean existsByProductCommonValuesAndEmail(ProductCommonValues product, String email);
    Optional<Review> findByProductCommonValuesAndEmailAndLastnameAndFirstname(ProductCommonValues product, String email, String lastname, String firstname);

    @Query("SELECT r FROM Review r " +
            "WHERE (:product IS NULL OR r.productCommonValues = :product) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (:ratingMin IS NULL OR :ratingMax IS NULL OR r.rating BETWEEN :ratingMin AND :ratingMax) " +
            "ORDER BY r.createdAt DESC")
    Page<Review> findAllReviewsByProductCommonValuesAndStatusAndRatingIsBetweenOrderByCreatedAtDesc(
            Pageable pageable,
            @Param("product") ProductCommonValues product,
            @Param("status") EReviewStatus status,
            @Param("ratingMin") BigDecimal ratingMin,
            @Param("ratingMax") BigDecimal ratingMax);
}
