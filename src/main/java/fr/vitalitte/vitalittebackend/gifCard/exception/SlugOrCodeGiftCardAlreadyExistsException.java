package fr.vitalitte.vitalittebackend.gifCard.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugOrCodeGiftCardAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugOrCodeGiftCardAlreadyExistsException(){super("Code déjà existant.");}
}
