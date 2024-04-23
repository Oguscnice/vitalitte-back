package fr.vitalitte.vitalittebackend.giftCardUsed.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class GiftCardAlreadyUsedException extends ResourceAlreadyExistException {
    public GiftCardAlreadyUsedException(){super("Carte Cadeau déjà utilisée !");}
}
