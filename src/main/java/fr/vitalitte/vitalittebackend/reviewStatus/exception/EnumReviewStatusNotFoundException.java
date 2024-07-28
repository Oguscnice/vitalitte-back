package fr.vitalitte.vitalittebackend.reviewStatus.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class EnumReviewStatusNotFoundException extends ResourceNotFoundException {
    public EnumReviewStatusNotFoundException() {super ("Status de traitement de l'avis non trouvé.");}
}
