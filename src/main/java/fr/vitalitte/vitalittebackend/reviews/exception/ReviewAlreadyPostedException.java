package fr.vitalitte.vitalittebackend.reviews.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class ReviewAlreadyPostedException extends ResourceAlreadyExistException {
    public ReviewAlreadyPostedException() {super ("Avis déjà posté par cet utilisateur.");}
}
