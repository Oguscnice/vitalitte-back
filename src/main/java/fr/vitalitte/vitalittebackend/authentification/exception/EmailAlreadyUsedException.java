package fr.vitalitte.vitalittebackend.authentification.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class EmailAlreadyUsedException extends ResourceAlreadyExistException {
    public EmailAlreadyUsedException(){
        super("L'email existe déjà.");
    }
}
