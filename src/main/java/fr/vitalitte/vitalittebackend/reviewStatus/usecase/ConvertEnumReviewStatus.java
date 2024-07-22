package fr.vitalitte.vitalittebackend.reviewStatus.usecase;

import fr.vitalitte.vitalittebackend.reviewStatus.exception.EnumReviewStatusNotFoundException;
import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import org.springframework.stereotype.Service;

@Service
public class ConvertEnumReviewStatus {
    public static String EReviewStatusToString(EReviewStatus eReviewStatus) {
        return switch (eReviewStatus) {
            case EN_ATTENTE_DE_VALIDATION -> "En attente de Validation";
            case ACCEPTE -> "Accepté";
            case REFUSE -> "Refusé";
            default -> throw new EnumReviewStatusNotFoundException();
        };
    }

    public static EReviewStatus StringToEReviewStatus(String reviewStatus) {
        return switch (reviewStatus) {
            case "En attente de Validation" -> EReviewStatus.EN_ATTENTE_DE_VALIDATION;
            case "Accepté" -> EReviewStatus.ACCEPTE;
            case "Refusé" -> EReviewStatus.REFUSE;
            default -> throw new EnumReviewStatusNotFoundException();
        };
    }
}
