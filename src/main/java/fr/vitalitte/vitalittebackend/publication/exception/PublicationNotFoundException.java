package fr.vitalitte.vitalittebackend.publication.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class PublicationNotFoundException extends ResourceNotFoundException {
    public PublicationNotFoundException(){super ("Publication non trouvée.");}
}
