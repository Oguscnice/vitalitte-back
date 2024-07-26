package fr.vitalitte.vitalittebackend.reviews.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.models.PaginationReviewsFiltered;
import fr.vitalitte.vitalittebackend.reviews.rest.CreateReviewBody;
import fr.vitalitte.vitalittebackend.reviews.rest.ReviewDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ReviewService {

    void createReview(CreateReviewBody createReviewBody);
    String changeReviewStatus(ReviewDto reviewDto);
    Page<ReviewDto> findReviewsPaginatedByProductSlugAndRatingBetween(PaginationReviewsFiltered paginationReviewsFiltered);

}
