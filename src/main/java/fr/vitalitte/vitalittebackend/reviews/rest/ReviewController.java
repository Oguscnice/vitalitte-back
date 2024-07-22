package fr.vitalitte.vitalittebackend.reviews.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.models.PaginationReviewsFiltered;
import fr.vitalitte.vitalittebackend.reviews.usecase.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/paginated")
    public ResponseEntity<Page<ReviewDto>> getReviewsPaginatedByStatus(@RequestBody PaginationReviewsFiltered paginationReviewsFiltered) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.reviewService.findReviewsPaginatedByProductSlugAndRatingBetween(paginationReviewsFiltered));
    }

    @PostMapping("/change-status")
    public ResponseEntity<MessageResponse> changeReviewStatus(@RequestBody ReviewDto reviewDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(this.reviewService.changeReviewStatus(reviewDto)));
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createReview(@RequestBody CreateReviewBody createReviewBody) {
        this.reviewService.createReview(createReviewBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Avis envoyé avec succès. Il sera soumis à un validation d'un adminisateur afin de vérifier qu'il respecte nos Règles de bonnes conduite."));
    }
}
