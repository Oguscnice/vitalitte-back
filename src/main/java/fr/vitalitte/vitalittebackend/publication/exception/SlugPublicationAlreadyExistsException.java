package fr.vitalitte.vitalittebackend.publication.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugPublicationAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugPublicationAlreadyExistsException() { super ("Publication déjà existante");}
}
