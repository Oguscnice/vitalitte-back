package fr.vitalitte.vitalittebackend.reviews.usecase;

import fr.vitalitte.vitalittebackend.reviewStatus.usecase.ConvertEnumReviewStatus;
import fr.vitalitte.vitalittebackend.reviews.exception.ReviewNotFoundException;
import fr.vitalitte.vitalittebackend.reviews.models.Review;
import fr.vitalitte.vitalittebackend.reviews.persistence.ReviewRepository;
import fr.vitalitte.vitalittebackend.reviews.rest.ReviewDto;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import fr.vitalitte.vitalittebackend.stationery.product.usecase.TransformProduct;
import org.springframework.stereotype.Service;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformReview {

    ReviewRepository reviewRepository;
    ProductRepository productRepository;
    TransformProduct transformProduct;

    public TransformReview(ProductRepository productRepository, ReviewRepository reviewRepository, TransformProduct transformProduct) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.transformProduct = transformProduct;
    }

    public ReviewDto reviewToDto(Review review) {

        String status = ConvertEnumReviewStatus.EReviewStatusToString(review.getStatus());
        Product product = this.productRepository.findBySlug(review.getProduct().getSlug()).orElseThrow(() -> new ProductNotFoundException("Produit"));
        ProductDto productDto = this.transformProduct.productToDto(product);

        return ReviewDto.builder()
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .lastname(review.getLastname())
                .firstname(review.getFirstname())
                .email(review.getEmail())
                .title(review.getTitle())
                .rating(review.getRating())
                .status(status)
                .productDto(productDto)
                .build();
    }

    public List<ReviewDto> reviewsToDtos(List<Review> reviews) {
        return mapList(this::reviewToDto, reviews);
    }

    public Review dtoToReview(ReviewDto reviewDto) {
        Product product = this.transformProduct.dtoToProduct(reviewDto.getProductDto());
        return this.reviewRepository.findByProductAndEmail(product, reviewDto.getEmail())
                .orElseThrow(ReviewNotFoundException::new);
    }

    public List<Review> dtosToReviews(List<ReviewDto> reviewDtoList) {
        return mapList(this::dtoToReview, reviewDtoList);
    }
}
