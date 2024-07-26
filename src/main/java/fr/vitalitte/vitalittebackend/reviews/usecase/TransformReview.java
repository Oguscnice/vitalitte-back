package fr.vitalitte.vitalittebackend.reviews.usecase;

import fr.vitalitte.vitalittebackend.reviewStatus.usecase.ConvertEnumReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.exception.ReviewNotFoundException;
import fr.vitalitte.vitalittebackend.reviews.models.Review;
import fr.vitalitte.vitalittebackend.reviews.persistence.ReviewRepository;
import fr.vitalitte.vitalittebackend.reviews.rest.ReviewDto;
import fr.vitalitte.vitalittebackend.stationery.common.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import fr.vitalitte.vitalittebackend.stationery.common.persistence.ProductCommonValuesRepository;
import fr.vitalitte.vitalittebackend.stationery.common.rest.ProductCommonValuesDto;
import fr.vitalitte.vitalittebackend.stationery.common.usecase.TransformProductCommonValues;
import org.springframework.stereotype.Service;

import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformReview {

    ReviewRepository reviewRepository;
    ProductCommonValuesRepository productCommonValuesRepository;
    TransformProductCommonValues transformProduct;

    public TransformReview(ReviewRepository reviewRepository, ProductCommonValuesRepository productCommonValuesRepository, TransformProductCommonValues transformProductCV) {
        this.reviewRepository = reviewRepository;
        this.productCommonValuesRepository = productCommonValuesRepository;
        this.transformProduct = transformProductCV;
    }

    public ReviewDto reviewToDto(Review review) {

        String status = ConvertEnumReviewStatus.EReviewStatusToString(review.getStatus());
        ProductCommonValues product = this.productCommonValuesRepository.findBySlug(review.getProduct().getSlug()).orElseThrow(ProductNotFoundException::new);
        ProductCommonValuesDto productDto = this.transformProduct.productCVToProductCVDto(product);

        return ReviewDto.builder()
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .lastname(review.getLastname())
                .firstname(review.getFirstname())
                .email(review.getEmail())
                .title(review.getTitle())
                .rating(review.getRating())
                .status(status)
                .productCommonValuesDto(productDto)
                .build();
    }

    public List<ReviewDto> reviewsToDtos(List<Review> reviews) {
        return mapList(this::reviewToDto, reviews);
    }

    public Review dtoToReview(ReviewDto reviewDto) {
        ProductCommonValues product = this.transformProduct.dtoToProductCV(reviewDto.getProductCommonValuesDto());
        return this.reviewRepository.findByProductCommonValuesAndEmailAndLastnameAndFirstname(product, reviewDto.getEmail(), reviewDto.getLastname(), reviewDto.getFirstname())
                .orElseThrow(ReviewNotFoundException::new);
    }

    public List<Review> dtosToReviews(List<ReviewDto> reviewDtoList) {
        return mapList(this::dtoToReview, reviewDtoList);
    }
}
