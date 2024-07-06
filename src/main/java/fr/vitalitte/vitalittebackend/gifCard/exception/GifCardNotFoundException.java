package fr.vitalitte.vitalittebackend.gifCard.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class GifCardNotFoundException extends ResourceNotFoundException {
    public GifCardNotFoundException(){super("Carte Cadeau non trouvée ou expirée.");}
}
