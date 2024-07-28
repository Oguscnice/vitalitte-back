package fr.vitalitte.vitalittebackend.reviews.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class ReviewNotFoundException extends ResourceNotFoundException {
    public ReviewNotFoundException(){super ("Avis introuvable.");}
}
