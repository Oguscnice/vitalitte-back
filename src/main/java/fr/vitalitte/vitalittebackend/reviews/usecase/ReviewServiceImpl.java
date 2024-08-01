package fr.vitalitte.vitalittebackend.reviews.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationReviewsFiltered;
import fr.vitalitte.vitalittebackend.reviewStatus.usecase.ConvertEnumReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.exception.ReviewAlreadyPostedException;
import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.models.Review;
import fr.vitalitte.vitalittebackend.reviews.persistence.ReviewRepository;
import fr.vitalitte.vitalittebackend.reviews.rest.CreateReviewBody;
import fr.vitalitte.vitalittebackend.reviews.rest.ReviewDto;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.usecase.TransformProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    ProductRepository productRepository;
    TransformReview transformReview;
    TransformProduct transformProduct;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, TransformReview transformReview, TransformProduct transformProduct) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.transformReview = transformReview;
        this.transformProduct = transformProduct;
    }

    @Override
    public void createReview(CreateReviewBody createReviewBody) {

        verifyIfReviewAlreadyExistsForThisProduct(createReviewBody);

        Product product = this.transformProduct.dtoToProduct(createReviewBody.getProductDto());

        final Review review = Review.builder()
                .content(createReviewBody.getContent())
                .lastname(createReviewBody.getLastname())
                .firstname(createReviewBody.getFirstname())
                .email(createReviewBody.getEmail())
                .title(createReviewBody.getTitle())
                .rating(createReviewBody.getRating())
                .status(EReviewStatus.EN_ATTENTE_DE_VALIDATION)
                .product(product)
                .build();

        this.reviewRepository.save(review);
    }

    @Override
    public String changeReviewStatus(ReviewDto reviewDto) {

        EReviewStatus statusUpdated = ConvertEnumReviewStatus.StringToEReviewStatus(reviewDto.getStatus());

        Review originalReview = this.transformReview.dtoToReview(reviewDto);
        originalReview.setStatus(statusUpdated);
        this.reviewRepository.save(originalReview);

        return "Le commentaire de " + reviewDto.getFirstname() + " est devenu : " + reviewDto.getStatus();
    }

    @Override
    public Page<ReviewDto> findReviewsPaginatedByProductSlugAndRatingBetween(PaginationReviewsFiltered paginationReviewsFiltered) {

        Pageable pageable = PageRequest.of(paginationReviewsFiltered.getPageableValues().getPageNumber(), paginationReviewsFiltered.getPageableValues().getPageSize());

        EReviewStatus eStatus = null;
        if (!paginationReviewsFiltered.getStatus().isBlank()) {
            eStatus = extractEStatusFromPaginationPaginationReviewsFiltered(paginationReviewsFiltered);
        }

        BigDecimal rating = paginationReviewsFiltered.getRating();
        BigDecimal ratingMax = rating.add(BigDecimal.valueOf(0.5));
        if (rating.equals(BigDecimal.ZERO)) {
            ratingMax = BigDecimal.valueOf(5);
        }

        Product product = null;
        if (paginationReviewsFiltered.getProductDto() != null) {
            product = this.transformProduct.dtoToProduct(paginationReviewsFiltered.getProductDto());
        }

        Page<Review> reviewsProduct = this.reviewRepository.findAllReviewsByProductAndStatusAndRatingIsBetweenOrderByCreatedAtDesc(pageable, product, eStatus, rating, ratingMax);

        List<ReviewDto> reviewDtoList = this.transformReview.reviewsToDtos(reviewsProduct.getContent());
        return new PageImpl<>(reviewDtoList, pageable, reviewsProduct.getTotalElements());
    }

    private Product findProductByProductSlug(String productSlug) {
        return this.productRepository.findBySlug(productSlug).orElseThrow(() -> new ProductNotFoundException("Produit"));
    }

    private EReviewStatus extractEStatusFromPaginationPaginationReviewsFiltered(PaginationReviewsFiltered paginationReviewsFiltered) {
        return ConvertEnumReviewStatus.StringToEReviewStatus(paginationReviewsFiltered.getStatus());
    }

    private void verifyIfReviewAlreadyExistsForThisProduct(CreateReviewBody createReviewBody) {
        Product product = findProductByProductSlug(createReviewBody.getProductDto().getSlug());
        if (this.reviewRepository.existsByProductAndEmail(product, createReviewBody.getEmail())) {
            throw new ReviewAlreadyPostedException();
        }
    }
}
