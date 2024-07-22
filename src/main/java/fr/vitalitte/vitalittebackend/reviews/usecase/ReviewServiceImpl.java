package fr.vitalitte.vitalittebackend.reviews.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationReviewsFiltered;
import fr.vitalitte.vitalittebackend.reviewStatus.usecase.ConvertEnumReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.exception.ReviewAlreadyPostedException;
import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.models.Review;
import fr.vitalitte.vitalittebackend.reviews.persistence.ReviewRepository;
import fr.vitalitte.vitalittebackend.reviews.rest.CreateReviewBody;
import fr.vitalitte.vitalittebackend.reviews.rest.ReviewDto;
import fr.vitalitte.vitalittebackend.stationery.common.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import fr.vitalitte.vitalittebackend.stationery.common.persistence.ProductCommonValuesRepository;
import fr.vitalitte.vitalittebackend.stationery.common.usecase.TransformProductCommonValues;
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
    ProductCommonValuesRepository productRepository;
    TransformReview transformReview;
    TransformProductCommonValues transformProduct;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductCommonValuesRepository productRepository, TransformReview transformReview, TransformProductCommonValues transformProduct) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.transformReview = transformReview;
        this.transformProduct = transformProduct;
    }

    @Override
    public void createReview(CreateReviewBody createReviewBody) {

        verifyIfReviewAlreadyExistsForThisProduct(createReviewBody);

        ProductCommonValues product = this.transformProduct.dtoToProductCV(createReviewBody.getProductCommonValuesDto());

        final Review review = Review.builder()
                .content(createReviewBody.getContent())
                .lastname(createReviewBody.getLastname())
                .firstname(createReviewBody.getFirstname())
                .email(createReviewBody.getEmail())
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
        BigDecimal ratingMax = rating.add(BigDecimal.ONE);
        if (rating.equals(BigDecimal.ZERO)) {
            ratingMax = BigDecimal.valueOf(5);
        }

        ProductCommonValues product = null;
        if (paginationReviewsFiltered.getProductCommonValuesDto() != null) {
            product = this.transformProduct.dtoToProductCV(paginationReviewsFiltered.getProductCommonValuesDto());
        }

        Page<Review> reviewsProduct = this.reviewRepository.findAllReviewsByProductCommonValuesAndStatusAndRatingIsBetweenOrderByCreatedAtDesc(pageable, product, eStatus, rating, ratingMax);

        List<ReviewDto> reviewDtoList = this.transformReview.reviewsToDtos(reviewsProduct.getContent());
        return new PageImpl<>(reviewDtoList, pageable, reviewsProduct.getTotalElements());
    }

    private ProductCommonValues findProductByProductSlug(String productSlug) {
        return this.productRepository.findBySlug(productSlug).orElseThrow(ProductNotFoundException::new);
    }

    private EReviewStatus extractEStatusFromPaginationPaginationReviewsFiltered(PaginationReviewsFiltered paginationReviewsFiltered) {
        return ConvertEnumReviewStatus.StringToEReviewStatus(paginationReviewsFiltered.getStatus());
    }

    private void verifyIfReviewAlreadyExistsForThisProduct(CreateReviewBody createReviewBody) {
        ProductCommonValues product = findProductByProductSlug(createReviewBody.getProductCommonValuesDto().getSlug());
        if (this.reviewRepository.existsByProductCommonValuesAndEmail(product, createReviewBody.getEmail())) {
            throw new ReviewAlreadyPostedException();
        }
    }
}
