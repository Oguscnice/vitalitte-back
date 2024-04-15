package fr.vitalitte.vitalittebackend.inscription.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugInscriptionAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugInscriptionAlreadyExistsException(){
        super("Personne déjà inscrite.");
    }
}
