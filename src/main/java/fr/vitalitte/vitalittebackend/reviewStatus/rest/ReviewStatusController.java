package fr.vitalitte.vitalittebackend.reviewStatus.rest;

import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import fr.vitalitte.vitalittebackend.reviewStatus.usecase.ConvertEnumReviewStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/review-status")
public class ReviewStatusController {

    ConvertEnumReviewStatus convertEnumReviewStatus;

    public ReviewStatusController(ConvertEnumReviewStatus convertEnumReviewStatus) {
        this.convertEnumReviewStatus = convertEnumReviewStatus;
    }

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getAllReviewStatusEnum() {

        List<String> types = new ArrayList<>();
        types.add(ConvertEnumReviewStatus.EReviewStatusToString(EReviewStatus.EN_ATTENTE_DE_VALIDATION));
        types.add(ConvertEnumReviewStatus.EReviewStatusToString(EReviewStatus.ACCEPTE));
        types.add(ConvertEnumReviewStatus.EReviewStatusToString(EReviewStatus.REFUSE));

        return types;
    }
}
