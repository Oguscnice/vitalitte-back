package fr.vitalitte.vitalittebackend.gifCard.exception;

import fr.vitalitte.vitalittebackend.common.exception.NotAvailableException;

public class GiftCardExpiredException extends NotAvailableException {
    public GiftCardExpiredException() {super("Carte Cadeau expirée.");}
}
